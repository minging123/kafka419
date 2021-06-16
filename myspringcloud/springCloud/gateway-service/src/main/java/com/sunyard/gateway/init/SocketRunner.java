package com.sunyard.gateway.init;

import com.sunyard.gateway.esbProxy.ESBProxyServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SocketRunner implements CommandLineRunner {
    @Autowired
    private ESBProxyServer proxyServer;

    @Override
    public void run(String... strings) {
//        new Thread(() -> {
//            try {
//                proxyServer.startServer();
//            } catch (InterruptedException e) {
//                log.error("Socket服务器引导线程异常退出！", e);
//            }
//        }).start();
    }
}

