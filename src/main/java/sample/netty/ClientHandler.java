package sample.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import javafx.scene.control.TextArea;

/**
 * @author sec
 * @version 1.0
 * @date 2020/12/13
 **/
public class ClientHandler extends SimpleChannelInboundHandler<RpcResponse> {

	private final TextArea textArea;

	public ClientHandler(TextArea textArea) {
		this.textArea = textArea;
	}

	/**
	 * 处理服务端返回的数据
	 */
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, RpcResponse response) {
		System.out.println("接受到server响应数据: " + response.toString());
		textArea.appendText(response.toString() + "\n\r");
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		ctx.close();
	}
}
