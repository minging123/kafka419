package com.kafka.udp;

import java.net.*;
import java.io.IOException;
import java.net.DatagramPacket;

public class UDPServerThread extends Thread {
    DatagramPacket datagramPacket = null;

    public UDPServerThread(DatagramPacket packet) {
        this.datagramPacket = packet;
    }

    @Override
    public void run() {
        super.run();
        InetAddress inetAddress = datagramPacket.getAddress();
        int port2 = datagramPacket.getPort();
        byte[] data2 = "欢迎你的到来".getBytes();
        DatagramPacket datagramPacket2 = new DatagramPacket(data2, data2.length, inetAddress, port2);

        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        try {
            socket.send(datagramPacket2);
        } catch (IOException e) {
            e.printStackTrace();
        }
        socket.close();
    }

}
