package io.maker.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NettyHttpServer {

	private static int port = 9999;

	public static void main(String[] args) throws InterruptedException {
		EventLoopGroup bossGroup = new NioEventLoopGroup(1);
		EventLoopGroup workerGroup = new NioEventLoopGroup(20);
		try {
			ServerBootstrap serverBootstrap = new ServerBootstrap();
			serverBootstrap.group(bossGroup, workerGroup)
					.option(ChannelOption.SO_BACKLOG, 128)
					.option(ChannelOption.SO_BROADCAST, null)
					.childOption(ChannelOption.SO_KEEPALIVE, true)
					.channel(NioServerSocketChannel.class)
					.childHandler(new ClientChannelInitializer());
			ChannelFuture future = serverBootstrap.bind(port).sync();
			
			future.addListener(new ChannelFutureListener() {
				@Override
				public void operationComplete(ChannelFuture future) throws Exception {
					if (future.isSuccess()) {
						log.info("server started at localhost:{}", port);
					}
				}
			});
			
			future.channel().closeFuture().sync();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
}