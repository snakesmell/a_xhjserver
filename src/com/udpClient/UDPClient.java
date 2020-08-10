package com.udpClient;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import com.udpUtil.CommandHex;

/*
 * 客户端
 */
public class UDPClient {
    public static void main(String[] args) throws IOException {
    	
		for(int i=0;i<1;i++) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
    	
		 String command="c0 10 20 10 03 00 01 00 80 23 00 00 00 00 00 00 c0"; 
//		 String command="C0 10 10 20 03 00 01 00 83 09 31 31 31 31 31 01 01 FE 1F BE FE FF FF FF 01 00 00 00 00 C0 "; 
		 
		 
		 byte[] bb = CommandHex.hexStringToByte(command);
		 
        /*
         * 向服务器端发送数据
         */
        // 1.定义服务器的地址、端口号、数据
        InetAddress address = InetAddress.getByName("192.168.10.241");
        int port = 17899;
//        byte[] data = "用户名：admin;密码：123".getBytes();
        // 2.创建数据报，包含发送的数据信息
        DatagramPacket packet = new DatagramPacket(bb, bb.length, address, port);
        // 3.创建DatagramSocket对象
        DatagramSocket socket = new DatagramSocket();
        // 4.向服务器端发送数据报
        socket.send(packet);
 
        /*
         * 接收服务器端响应的数据
         */
//         1.创建数据报，用于接收服务器端响应的数据
//        byte[] data2 = new byte[1024];
//        DatagramPacket packet2 = new DatagramPacket(data2, data2.length);
//        // 2.接收服务器响应的数据
//        socket.receive(packet2);
//        // 3.读取数据
//        String reply = new String(data2, 0, packet2.getLength());
//        System.out.println("我是客户端，服务器说：" + reply);
        // 4.关闭资源
        socket.close();
    }
    }
}
