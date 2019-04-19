package com.kafka.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UDPServer {

    public static void main(String[] args) throws SocketException {
        DatagramSocket socket = new DatagramSocket(8800);

        byte[] data = new byte[1024];
        DatagramPacket packet = new DatagramPacket(data, data.length);

        System.out.println(".....服务器准备启动......");
        int count = 0;

        while (true) {

            try {
                socket.receive(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }

            count++;

            UDPServerThread udpServerThread = new UDPServerThread(packet);
            udpServerThread.start();

            String info = new String(data, 0, packet.getLength());

            System.out.println("我是服务器,客户端说:" + info);
            System.out.println("该客户端IP地址为:" + packet.getAddress());
            System.out.println("客户端连接次数:" + count);

        }

    }

}
