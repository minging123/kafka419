package com.sunyard.gateway.esbProxy;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ESBProxyServer {
    @Value("${esb.proxy.external-port}")
    private int externalPort;

    public void startServer() throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
            .channel(NioServerSocketChannel.class)
            .childHandler(new EsbServerInitializer())
            .option(ChannelOption.SO_BACKLOG, 2014)
            .childOption(ChannelOption.SO_KEEPALIVE, true);

            log.info("接口代理服务开启监听端口[{}]，开始服务", externalPort);
            ChannelFuture f = b.bind(externalPort).sync();

            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
