package com.upttest;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class uuljk {
	
	public static void main(String[] args) {
		new uuljk().ss();
	}
	
	private final int PORT = 17899;
    private DatagramSocket datagramSocket;
    
    public void ss() {
		
		// TODO Auto-generated method stub
		
//		-64, 16, 16, 32, 1, 0, 1, 0, -127, 1, 1, 1, 1, 1, 1, 0, -64
		
		
		
		try {
			byte[] buffer = new byte[1024];
			buffer[0]=-64;
			buffer[1]=16;
			buffer[2]=16;
			buffer[3]=32;
			buffer[4]=1;
			buffer[5]=0;
			buffer[6]=1;
			buffer[7]=0;
			buffer[8]=-127;
			buffer[9]= 1;
			buffer[10]=1;
			buffer[11]=1;
			buffer[12]=1;
			buffer[13]=1;
			buffer[14]=1;
			buffer[15]=0;
			buffer[16]=-64;
			datagramSocket = new DatagramSocket(PORT);
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            packet.setData(buffer);
			datagramSocket.send(packet);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
