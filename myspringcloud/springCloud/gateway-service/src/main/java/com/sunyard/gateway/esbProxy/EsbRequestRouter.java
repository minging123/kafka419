package com.sunyard.gateway.esbProxy;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.util.List;

/**
 * ESB消息转发服务。
 * @author ln
 *
 */
@Component("esbRequestBridge")
@Sharable
public class EsbRequestRouter extends MessageToMessageDecoder<String> {
	
	private Logger log = LoggerFactory.getLogger(getClass());
	
//	@Autowired
//	IDataRouter<String> router;
	
	@Autowired
	ApplicationContext applicationContext;

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ctx.writeAndFlush("scoketClient客户端: "+ InetAddress.getLocalHost().getHostName() + " 与服务端建立连接!\n");
		super.channelActive(ctx);
	}


	@Override
	protected void decode(ChannelHandlerContext ctx, String msg, List<Object> out) {
		log.debug("decode：\n{}", msg);
		out.add(msg);
	}


	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		log.debug("channelRead：\n{}", msg);
		//super.channelRead(ctx, desc);
//		String route = router.route(desc.toString());
//		ctx.channel().writeAndFlush(route);
//		log.debug("writeAndFlush：\n{}", route);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		log.debug("channelReadComplete：\n{}", "消息接收完成");
		//super.channelReadComplete(ctx);
		ctx.channel().writeAndFlush("scoketClient客户端: "+ InetAddress.getLocalHost().getHostName() + " 与服务端断开连接!\n");
		ctx.channel().close();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		log.debug("exceptionCaught：\n{}", cause);
		super.exceptionCaught(ctx, cause);
	}
	

}
