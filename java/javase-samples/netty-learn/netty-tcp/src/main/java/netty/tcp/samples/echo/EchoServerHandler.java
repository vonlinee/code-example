package netty.tcp.samples.echo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

public class EchoServerHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		super.channelRegistered(ctx);
		// io.netty.channel.socket.nio.NioSocketChannel
		System.out.println("channelRegistered 客户端：" + ctx.channel().remoteAddress());
	}

	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
		super.channelUnregistered(ctx);
		System.out.println("channelUnregistered " + ctx.channel().getClass());
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		super.channelInactive(ctx);
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		super.userEventTriggered(ctx, evt);
	}

	@Override
	public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
		super.channelWritabilityChanged(ctx);
	}

	@Override
	protected void ensureNotSharable() {
		super.ensureNotSharable();
	}

	@Override
	public boolean isSharable() {
		// TODO Auto-generated method stub
		return super.isSharable();
	}

	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		super.handlerAdded(ctx); // Empty Implementation in ChannelInboundHandlerAdapter
		ChannelHandler handler = ctx.handler();
		System.out.println("handlerAdded: => " + handler);
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		super.handlerRemoved(ctx);
		ChannelHandler handler = ctx.handler();
		System.out.println("handlerRemoved: => " + handler);
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf in = (ByteBuf) msg;
		System.out.println("Server received: " + in.toString(CharsetUtil.UTF_8));
		ctx.write(in);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
}