package com.xwd.study.rpc.remoting.model;

import static com.xwd.study.rpc.common.protocal.LaopopoProtocol.HEAD_LENGTH;

import static com.xwd.study.rpc.common.protocal.LaopopoProtocol.HEARTBEAT;
import static com.xwd.study.rpc.common.protocal.LaopopoProtocol.MAGIC;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * 
 * @author BazingaLyn
 * @description
 * @time
 * @modifytime
 */
@SuppressWarnings("deprecation")
public class Heartbeats {

    private static final ByteBuf HEARTBEAT_BUF;
    
    static {
        ByteBuf buf = Unpooled.buffer(HEAD_LENGTH);
        buf.writeShort(MAGIC);
        buf.writeByte(HEARTBEAT);
        buf.writeByte(0);
        buf.writeLong(0);
        buf.writeInt(0);
        HEARTBEAT_BUF = Unpooled.unmodifiableBuffer(Unpooled.unreleasableBuffer(buf));
    }

    /**
     * Returns the shared heartbeat content.
     */
    public static ByteBuf heartbeatContent() {
        return HEARTBEAT_BUF.duplicate();
    }
}
