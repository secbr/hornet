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
     * process the data retuned by the server
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("server response message：" + buf.toString(CharsetUtil.UTF_8));
        textArea.appendText("received message：" + buf.toString(CharsetUtil.UTF_8) + "\n\r");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println("successfully connected to the server");
        // send message to the server when the connection is successful
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello server!", CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
    }
}
