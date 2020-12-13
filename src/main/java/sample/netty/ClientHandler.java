package sample.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import javafx.scene.control.TextArea;

/**
 * @author sec
 * @version 1.0
 * @date 2020/12/13
 **/
public class ClientHandler extends ChannelInboundHandlerAdapter {

	private final TextArea textArea;

	public ClientHandler(TextArea textArea) {
		this.textArea = textArea;
	}

	/**
	 * 处理服务端返回的数据
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		ByteBuf buf = (ByteBuf) msg;
		System.out.println("服务端返回的信息：" + buf.toString(CharsetUtil.UTF_8));
		textArea.appendText("received message：" + buf.toString(CharsetUtil.UTF_8) + "\n\r");
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		// 发送数据
		System.out.println("连接上了 服务器....");
		ctx.writeAndFlush(Unpooled.copiedBuffer("哈哈 你好呀!!!", CharsetUtil.UTF_8));
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		ctx.close();
	}
}
