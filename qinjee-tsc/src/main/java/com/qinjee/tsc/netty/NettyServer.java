package com.qinjee.tsc.netty;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

@Component
public class NettyServer {

	private static Logger logger = LogManager.getLogger(NettyServer.class);

	// boss事件轮询线程组
	private EventLoopGroup bossGroup = new NioEventLoopGroup();
	// worker事件轮询线程组
	private EventLoopGroup workerGroup = new NioEventLoopGroup();
	// 通道
	private Channel channel;
	
	@Value("${netty.port}")
    private Integer port;
	
	@Autowired
	private ServerChannelInitializer serverChannelInitializer;

	public void start() throws Exception {
		try {
			ServerBootstrap serverBootstrap = new ServerBootstrap();
			serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).localAddress(port)
					.option(ChannelOption.SO_BACKLOG, 128).childOption(ChannelOption.SO_KEEPALIVE, true)
					.childHandler(serverChannelInitializer);
			// Future：异步操作的结果
			ChannelFuture channelFuture = serverBootstrap.bind(port).syncUninterruptibly();
			channel = channelFuture.channel();

			if (channelFuture != null && channelFuture.isSuccess()) {
				logger.info("Netty server start success，port = {}", port);
			} else {
				logger.info("Netty server start fail");
			}

		} finally {
//			destroy(); 
		}
	}

	/**
	 *	停止Netty服务 功能描述：
	 * 
	 * @author 周赟
	 *
	 * @since 2019年6月27日
	 *
	 * @update:[变更日期YYYY-MM-DD][更改人姓名][变更描述]
	 */
	public void destroy() {
		if (channel != null) {
			channel.close();
		}
		workerGroup.shutdownGracefully();
		bossGroup.shutdownGracefully();
		logger.info("Netty server shutdown success");
	}

}
