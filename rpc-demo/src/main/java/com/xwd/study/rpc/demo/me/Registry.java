package com.xwd.study.rpc.demo.me;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
/**
 * @author xwd	
 * @throws IOException 
 * @time 2018年1月16日 下午4:37:44 
 */
public class Registry {

	private static int port=6666;//注册中心监听端口
	private static String ProviderType="001";//服务注册类型
	private static String ConsumerType="002";//服务发现类型
	private static Map<String,HashSet<Service>> registryMap=new HashMap<>();
	public static void main(String[] args) throws IOException{
		ServerSocket server = new ServerSocket(port); 
		System.out.println("注册中心 服务启动完成");
        for(;;) {  
            try {  
                final Socket socket = server.accept();  
                
                new Thread(new Runnable() {  
                    @Override  
                    public void run() {
                    	HashSet<Service> ipPorts=null;
                    	String ipPort=null;
                        try {  
                            try {  
                                ObjectInputStream input = new ObjectInputStream(socket.getInputStream()); 
                                ObjectOutputStream output = null;
                                try {
                                	String messageType=input.readUTF();
                                	RegistryRe registryRe=new RegistryRe();
                                	Service service=(Service)input.readObject();
                                	if(ProviderType.compareTo(messageType)==0){//注册服务
                                        if(!registryMap.containsKey(service.getServiceName()+":"+service.getServiceVersion())){
                                        	ipPorts=new HashSet<>();
                                        	registryMap.put(service.getServiceName()+":"+service.getServiceVersion(), ipPorts);
                                        }
                                        ipPorts=registryMap.get(service.getServiceName()+":"+service.getServiceVersion());
                                        ipPorts.add(service);
                                        output = new ObjectOutputStream(socket.getOutputStream());
                                        registryRe.setStatus("SUCCESS");
                                        registryRe.setMsg("注册成功");
                                        output.writeObject(registryRe);
                                        System.out.println(service+" 注册成功");
                                	}else{//获取服务
                                		if(!registryMap.containsKey(service.getServiceName()+":"+service.getServiceVersion())){
                                			output = new ObjectOutputStream(socket.getOutputStream());
                                			registryRe.setStatus("NOT_FIND_SERVICE");
                                            registryRe.setMsg("没找到服务");;
                                			output.writeObject(registryRe);
                                			System.out.println(service +" 服务没找到");
                                        }else{
                                        	output = new ObjectOutputStream(socket.getOutputStream());
                                        	registryRe.setStatus("SUCCESS");
                                        	registryRe.setServices(registryMap.get(service.getServiceName()+":"+service.getServiceVersion()));
                                        	output.writeObject(registryRe);
//                                        	ipPorts=registryMap.get(serviceName+":"+serviceVersion);
                                        	System.out.println(service+" 获取成功");
//                                        	Iterator<String> iterator=ipPorts.iterator();
//                                        	if(iterator.hasNext()){
//                                        		ipPort=iterator.next();
//                                        		output.writeUTF("SUCCESS");
//                                        		output.writeUTF(ipPort.split(":")[0]);
//                                        		output.writeUTF(ipPort.split(":")[1]);
//                                        		System.out.println(serviceName+":"+serviceVersion+":"+ipPort.split(":")[0]+":"+ipPort.split(":")[1]+"获取成功");
//                                        	}else{
//                                        		output.writeUTF("NOT_FIND_SERVICE");
//                                        		output.writeObject(1);
//                                        		System.out.println(serviceName+":"+serviceVersion+"服务没找到");
//                                        	}
                                        }
                                	}
                                } finally {  
                                	try{
                                		input.close();  
                                	}finally{
                                		if(!socket.isClosed()){
                                			output.close();
                                		}
                                		
                                	}
                                }  
                            } finally {
                            	if(!socket.isClosed()){
                            		socket.close();  
                            	}
                                
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
}
