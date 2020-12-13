package sample.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import javafx.scene.control.TextArea;

/**
 * @author sec
 * @version 1.0
 * @date 2020/12/13
 **/
public class NettyClient {

	private final String host;
	private final int port;
	private Channel channel;
	private final TextArea textArea;
	final EventLoopGroup group = new NioEventLoopGroup();

	public NettyClient(String host, int port, TextArea textArea) {
		this.host = host;
		this.port = port;
		this.textArea = textArea;
	}

	public void start() throws Exception {
		Bootstrap b = new Bootstrap();
		b.group(group).channel(NioSocketChannel.class)
				.handler(new ChannelInitializer<SocketChannel>() {
					@Override
					public void initChannel(SocketChannel ch) {
						System.out.println("connecting...");
						ChannelPipeline pipeline = ch.pipeline();
						pipeline.addLast(new RpcEncoder(RpcRequest.class));
						pipeline.addLast(new RpcDecoder(RpcResponse.class));
						pipeline.addLast(new ClientHandler(textArea));
					}
				});
		//发起异步连接请求，绑定连接端口和host信息
		final ChannelFuture future = b.connect(host, port).sync();

		future.addListener((ChannelFutureListener) arg0 -> {
			if (future.isSuccess()) {
				System.out.println("connect success");
			} else {
				System.out.println("connect failed");
				future.cause().printStackTrace();
				group.shutdownGracefully(); //关闭线程组
			}
		});

		this.channel = future.channel();
	}

	public Channel getChannel() {
		return channel;
	}

	public void close() {
		if (channel != null) {
			textArea.appendText("close connection\n\r");
			channel.close();
		}
	}
}
