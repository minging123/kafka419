package com.sunyard.gateway.esbProxy;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import java.nio.charset.Charset;

/**
 * ESB代理服务socket通道初始化类。
 * 
 * @author ln
 *
 */
@Slf4j
public class EsbServerInitializer extends ChannelInitializer<SocketChannel> {
	@Value("#{esbDataConfig.externalEncoding?: 'UTF-8'}")
	protected String contentEncoding = "UTF-8";
	
	@Value("#{esbDataConfig.contentMaxLength?: 100000}")
	protected int contentMaxLength = 100000;
	
	@Value("#{esbDataConfig.dataHeaderLength?: 8}")
	protected int dataHeaderLength = 8;

	@Override
	protected void initChannel(SocketChannel ch) {
		log.debug("本地连接：\n{}", "success");
		ChannelPipeline pipeline = ch.pipeline();
		pipeline.addLast(new LengthFieldPrepender(dataHeaderLength));
		pipeline.addLast(new LengthFieldBasedFrameDecoder(contentMaxLength, 0, dataHeaderLength, 0, dataHeaderLength));
		pipeline.addLast(new StringEncoder(Charset.forName(this.contentEncoding)));
		pipeline.addLast(new StringDecoder(Charset.forName(this.contentEncoding)));
//		pipeline.addLast(lastHandler);
		
	}
}
