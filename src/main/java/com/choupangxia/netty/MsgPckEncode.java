package com.choupangxia.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.msgpack.MessagePack;

/**
 * Created by jimi on 2019/7/19
 * Comments:
 * For:
 */
public class MsgPckEncode extends MessageToByteEncoder<Object> {

	@Override
	protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf buf)
			throws Exception {
		// TODO Auto-generated method stub
		MessagePack pack = new MessagePack();

		byte[] write = pack.write(msg);

		buf.writeBytes(write);
	}
}
