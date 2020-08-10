package com.upttest;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class U2 {

	public static void main(String[] args) {
//		c0 10 20 10 01 00 01 00 83 01 00 00 00 00 00 78 7a 32 31 30 30 00 c0
//		c0 10 20 10 02 00 01 00 80 04 00 00 00 00 00 00 c0
		 byte []c=new byte[23];
		 c[0]=(byte) (0XC0 & 0XFF);
		 c[1]=(byte) (0X10 & 0XFF);
		 c[2]=(byte) (0X20 & 0XFF);
		 c[3]=(byte) (0X10 & 0XFF);
		 c[4]=(byte) (0X01 & 0XFF);
		 c[5]=(byte) (0X00 & 0XFF);
		 c[6]=(byte) (0X01 & 0XFF);
		 c[7]=(byte) (0X00 & 0XFF);
		 c[8]=(byte) (0X83 & 0XFF);
		 c[9]=(byte) (0X01 & 0XFF);
		 c[10]=(byte) (0X00 & 0XFF);
		 c[11]=(byte) (0X00 & 0XFF);
		 c[12]=(byte) (0X00 & 0XFF);
		 c[13]=(byte) (0X00 & 0XFF);
		 c[14]=(byte) (0X00 & 0XFF);
		 c[15]=(byte) (0X78 & 0XFF);
		 c[16]=(byte) (0X7a & 0XFF);
		 c[17]=(byte) (0X32 & 0XFF);
		 c[18]=(byte) (0X31 & 0XFF);
		 c[19]=(byte) (0X30 & 0XFF);
		 c[20]=(byte) (0X30 & 0XFF);
		 c[21]=(byte) (0X00 & 0XFF);
		 c[22]=(byte) (0XC0 & 0XFF);
		 
		
		 byte []b=new byte[17];
		 b[0]=(byte) (0XC0 & 0XFF);
		 b[1]=(byte) (0X10 & 0XFF);
		 b[2]=(byte) (0X20 & 0XFF);
		 b[3]=(byte) (0X10 & 0XFF);
		 b[4]=(byte) (0X02 & 0XFF);
		 b[5]=(byte) (0X00 & 0XFF);
		 b[6]=(byte) (0X01 & 0XFF);
		 b[7]=(byte) (0X00 & 0XFF);
		 b[8]=(byte) (0X80 & 0XFF);
		 b[9]=(byte) (0X04 & 0XFF);
		 b[10]=(byte) (0X00 & 0XFF);
		 b[11]=(byte) (0X00 & 0XFF);
		 b[12]=(byte) (0X00 & 0XFF);
		 b[13]=(byte) (0X00 & 0XFF);
		 b[14]=(byte) (0X00 & 0XFF);
		 b[15]=(byte) (0X00 & 0XFF);
		 b[16]=(byte) (0XC0 & 0XFF);
		 
		 try {
			 InetAddress address = InetAddress.getByName("192.168.10.241");
			 DatagramPacket datagramPacket = new DatagramPacket(c, c.length, address, 17899);
			 DatagramSocket socket = new DatagramSocket();
			 socket.send(datagramPacket);
//			 Thread.sleep(1000);
			 DatagramPacket datagramPacket2 = new DatagramPacket(b, b.length, address, 17899);
			 socket.send(datagramPacket2);
			 
			 
			 
			 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		
	}
	
}
