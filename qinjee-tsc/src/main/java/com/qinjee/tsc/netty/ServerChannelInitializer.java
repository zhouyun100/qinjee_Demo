package com.qinjee.tsc.netty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

@Component
public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {

	@Autowired
	private ServerChannelHandler serverChannelHandler;

	protected void initChannel(SocketChannel socketChannel) throws Exception {
		ChannelPipeline cp = socketChannel.pipeline();
		cp.addLast("decoder", new StringDecoder(CharsetUtil.UTF_8));
		cp.addLast("encoder", new StringEncoder(CharsetUtil.UTF_8));
		cp.addLast(serverChannelHandler);
	}
}
