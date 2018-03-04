package com.xwd.study.rpc.demo.me;
import java.io.IOException;
import java.io.ObjectInputStream;  
import java.io.ObjectOutputStream;  
import java.lang.reflect.InvocationHandler;  
import java.lang.reflect.Method;  
import java.lang.reflect.Proxy;  
import java.net.ServerSocket;  
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.print.attribute.HashAttributeSet;  
/**
 * @author xwd	
 * @time 2018年1月16日 下午4:08:28 
 */
public class RpcFramework {
	private static String registryIp="127.0.0.1";//注册中心监听端口 
	private static int registryPort=6666;//注册中心监听端口  
	private static String ProviderType="001";
	private static String ConsumerType="002";
	private static Map<String,Object> services=new HashMap<>();
	private static Map<String,List<Service>> findServices=new HashMap<>();
	    /** 
	     * 暴露服务 
	     *  
	     * @param service 服务实现 
	     * @param port 服务端口 
	     * @throws Exception 
	     */  
	    public static void export(final String serviceName,final Object service,final String serviceVersion,int servicePort) throws Exception {
	        if (service == null)  
	            throw new IllegalArgumentException("service instance == null");  
	        if (servicePort <= 0 || servicePort > 65535)  
	            throw new IllegalArgumentException("Invalid port " + servicePort);  
	        ServerSocket server = new ServerSocket(servicePort);
	        System.out.println("Export service " + service.getClass().getName() + " on port " + servicePort);
	        registryService(serviceName,serviceVersion,servicePort);
	        services.put(serviceName+":"+serviceVersion, service);
	        for(;;) {  
	            try {  
	                final Socket socket = server.accept();  
	                new Thread(new Runnable() {
	                    @Override  
	                    public void run() {  
	                        try {  
	                            try {  
	                                ObjectInputStream input = new ObjectInputStream(socket.getInputStream());  
	                                try {
	                                	Service servic=(Service)input.readObject();
	                                    String methodName = input.readUTF();  
	                                    Class<?>[] parameterTypes = (Class<?>[])input.readObject();  
	                                    Object[] arguments = (Object[])input.readObject();  
	                                    ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());  
	                                    try {  
	                                        Method method = service.getClass().getMethod(methodName, parameterTypes);  
	                                        Object result = method.invoke(services.get(servic.getServiceName()+":"+servic.getServiceVersion()), arguments);  
	                                        output.writeObject(result);  
	                                    } catch (Throwable t) {  
	                                        output.writeObject(t);  
	                                    } finally {  
	                                        output.close();  
	                                    }  
	                                } finally {  
	                                    input.close();  
	                                }  
	                            } finally {  
	                                socket.close();  
	                            }  
	                        } catch (Exception e) {  
	                            e.printStackTrace();  
	                        }  
	                    }  
	                }).start();  
	            } catch (Exception e) {  
	                e.printStackTrace();  
	            }  
	        }
	        
	    }
	    /**
	     * 
	     * @author xwd	
	     * @time 2018年1月16日 下午6:52:51
	     * @param service 服务名称
	     * @param serviceVersion 服务版本
	     * @param servicePort 服务端口
	     */
	    private static void registryService(String serviceName,String serviceVersion,int servicePort) throws UnknownHostException, IOException{
	    	try{
	    		Socket socket = new Socket(registryIp, registryPort);
	    		ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
	    		ObjectInputStream intput = null; 
	    		try{
		    		 output.writeUTF(ProviderType);
		    		 Service service=new Service();
		    		 service.setServiceName(serviceName);
		    		 service.setServiceVersion(serviceVersion);
		    		 service.setServiceIp("127.0.0.1");
		    		 service.setServicePort(servicePort);
		    		 output.writeObject(service);
		    		 intput = new ObjectInputStream(socket.getInputStream());
		    		 RegistryRe registryRe=(RegistryRe)intput.readObject();
		    		 if("SUCCESS".compareTo(registryRe.getStatus())==0){
		    			 System.out.println(service+" 服务注册成功");
		    		 }else{
		    			 System.out.println(service+" 服务注册失败"); 
		    		 }
	    		}finally{
	    			try{
	    				output.close();
	    			}finally{
	    				try{
	    					intput.close();
	    				}finally{
	    					socket.close();
	    				}
	    				
	    			}
	    		}
	    	}catch(Exception e){
	    		e.printStackTrace();
	    	}
	    }
	  
	    /** 
	     * 引用服务 
	     *  
	     * @param <T> 接口泛型 
	     * @param interfaceClass 接口类型 
	     * @param host 服务器主机名 
	     * @param port 服务器端口 
	     * @return 远程服务 
	     * @throws Exception 
	     */  
	    @SuppressWarnings("unchecked")  
	    public static <T> T refer(final Class<T> interfaceClass,final String serviceVersion) throws Exception {  
	        if (interfaceClass == null)  
	            throw new IllegalArgumentException("Interface class == null");  
	        if (! interfaceClass.isInterface())  
	            throw new IllegalArgumentException("The " + interfaceClass.getName() + " must be interface class!");  	         
	        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class<?>[] {interfaceClass}, new InvocationHandler() {  
	            public Object invoke(Object proxy, Method method, Object[] arguments) throws Throwable {  
	            	Service service=findService(interfaceClass.getName(),serviceVersion);
	            	Socket socket = new Socket(service.getServiceIp(),service.getServicePort());  
	                try {  
	                    ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());  
	                    try {
	                    	output.writeObject(service);
	                        output.writeUTF(method.getName());  
	                        output.writeObject(method.getParameterTypes());  
	                        output.writeObject(arguments);  
	                        ObjectInputStream input = new ObjectInputStream(socket.getInputStream());  
	                        try {  
	                            Object result = input.readObject();  
	                            if (result instanceof Throwable) {  
	                                throw (Throwable) result;  
	                            }  
	                            return result;  
	                        } finally {  
	                            input.close();  
	                        }  
	                    } finally {  
	                        output.close();  
	                    }  
	                } finally {  
	                    socket.close();  
	                }  
	            }  
	        });  
	    }
	    
	    /**
	     * 
	     * @author xwd	
	     * @time 2018年1月16日 下午6:52:51
	     * @param service 服务名称
	     * @param serviceVersion 服务版本
	     * @param servicePort 服务端口
	     */
	    private static Service findService( String serviceName,String serviceVersion) throws UnknownHostException, IOException{
	    	Service reService=null;
	    	try{
	    		Socket socket = new Socket(registryIp, registryPort);
	    		ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
	    		ObjectInputStream intput = null; 
	    		try{ 
	    			 List<Service> list=null;
	    			 if(!findServices.containsKey(serviceName+":"+serviceVersion)){
	    				 output.writeUTF(ConsumerType);
			    		 Service service=new Service();
			    		 service.setServiceName(serviceName);
			    		 service.setServiceVersion(serviceVersion);
			    		 output.writeObject(service);
			    		 intput = new ObjectInputStream(socket.getInputStream());
			    		 RegistryRe registryRe=(RegistryRe)intput.readObject();
			    		 if("SUCCESS".compareTo(registryRe.getStatus())==0){
			    			 Iterator<Service> iterator=registryRe.getServices().iterator();
			    			 list=new ArrayList<>();
	                     	while(iterator.hasNext()){
	                     		list.add(iterator.next());
	                     	}
	                     	findServices.put(serviceName+":"+serviceVersion, list);
	                     	System.out.println(registryRe+" 注册中心服务查找成功");
			    		 }else{
			    			 System.out.println(registryRe+"注册中心服务查找失败"); 
			    		 }
	    			 }
	    			 list=findServices.get(serviceName+":"+serviceVersion);
	    			 if(list!=null && !list.isEmpty()){
	    				 reService=list.get(new Random().nextInt(list.size()));
	    			 }
	    		}finally{
	    			try{
	    				output.close();
	    			}finally{
	    				try{
	    					if(intput!=null){
	    						intput.close();
	    					}
	    				}finally{
	    					socket.close();
	    				}
	    				
	    			}
	    		}
	    	}catch(Exception e){
	    		e.printStackTrace();
	    	}
	    	return reService;
	    }
}
