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
	private TextArea textArea;
	final EventLoopGroup group = new NioEventLoopGroup();

	//连接服务端的端口号地址和端口号
	public NettyClient(String host, int port, TextArea textArea) {
		this.host = host;
		this.port = port;
		this.textArea = textArea;
	}

	public void start() throws Exception {
		Bootstrap b = new Bootstrap();
		b.group(group).channel(NioSocketChannel.class)  // 使用NioSocketChannel来作为连接用的channel类
				.handler(new ChannelInitializer<SocketChannel>() { // 绑定连接初始化器
					@Override
					public void initChannel(SocketChannel ch) throws Exception {
						System.out.println("正在连接中...");
						ChannelPipeline pipeline = ch.pipeline();
						pipeline.addLast(new RpcEncoder(RpcRequest.class)); //编码request
						pipeline.addLast(new RpcDecoder(RpcResponse.class)); //解码response
						pipeline.addLast(new ClientHandler(textArea)); //客户端处理类
					}
				});
		//发起异步连接请求，绑定连接端口和host信息
		final ChannelFuture future = b.connect(host, port).sync();

		future.addListener((ChannelFutureListener) arg0 -> {
			if (future.isSuccess()) {
				System.out.println("连接服务器成功");

			} else {
				System.out.println("连接服务器失败");
				future.cause().printStackTrace();
				group.shutdownGracefully(); //关闭线程组
			}
		});

		this.channel = future.channel();
	}

	public Channel getChannel() {
		return channel;
	}

	public void closeGroup(){
		if(channel != null){
			channel.close();
		}
		//关闭线程组
//		group.shutdownGracefully();
	}
}
