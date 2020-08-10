package com.upttest;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

/***
 * UDP Client端
 ***/
public class UdpClient {
   
    private String sendStr = "hello";
    private String netAddress = "192.168.10.241";
    private final int PORT = 17899;
   
    private DatagramSocket datagramSocket;
    private DatagramPacket datagramPacket;
   
    public  byte[] short2byte(short s){
        byte[] b = new byte[2]; 
        for(int i = 0; i < 2; i++){
            int offset = 16 - (i+1)*8; //因为byte占4个字节，所以要计算偏移量
            b[i] = (byte)((s >> offset)&0xff); //把16位分为2个8位进行分别存储
        }
        return b;
   }
    private byte[] shortArr2byteArr(short[] shortArr, int shortArrLen){
        byte[] byteArr = new byte[shortArrLen * 2];
        ByteBuffer.wrap(byteArr).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().put(shortArr);
        return byteArr;
    }

    public void UdpClient2(){
        try {
        	
        	 List result = new ArrayList();
        	
        	short[] s = new short[1024];
        	
        	s[0]=0XC0;//帧开始
        	s[1]=0X10;//版本号
        	s[2]=0X10;//发送方标识
        	s[3]=0X20;//接收方标识
        	s[4]=0X10;//数据链路码
        	s[5]=0X00;//区域号
        	s[6]=0X01;//路口号1
        	s[7]=0X00;//路口号2
        	s[8]=0X81;//操作类型
        	
        	s[9]=0XC1; //对象标识
        	s[10]=0X01;
        	s[11]=0X01;
        	s[12]=0X01;
        	s[13]=0X01;
        	
        	s[14]=0X01;//保留
        				//数据内容
        	String xz="xz2100";
        	char[] xc = xz.toCharArray();
        	byte b1 =(byte)xc[0];
        	byte b2 =(byte)xc[1];
        	byte b3 =(byte)xc[2];
        	byte b4 =(byte)xc[3];
        	byte b5 =(byte)xc[4];
        	byte b6 =(byte)xc[5];
        	s[15]=0X00;
        	s[16]=0XC0;
//    
//			
			byte[] by0 = short2byte(s[0]);
			byte[] by1 = short2byte(s[1]);
			byte[] by2 = short2byte(s[2]);
			byte[] by3 = short2byte(s[3]);
			byte[] by4 = short2byte(s[4]);
			byte[] by5 = short2byte(s[5]);
			byte[] by6 = short2byte(s[6]);
			byte[] by7 = short2byte(s[7]);
			byte[] by8 = short2byte(s[8]);
			byte[] by9 = short2byte(s[9]);
			byte[] by10 = short2byte(s[10]);
			byte[] by11 = short2byte(s[11]);
			byte[] by12 = short2byte(s[12]);
			byte[] by13 = short2byte(s[13]);
			byte[] by14 = short2byte(s[14]);
			
			byte[] by15 = short2byte(s[15]);
			byte[] by16 = short2byte(s[16]);
			
			ArrayList<Byte> list = new ArrayList<Byte>();
			
			for(int i=0;i<=14;i++){
				byte[] sss = short2byte(s[i]);
				for(int j=0;j<2;j++){
					list.add(sss[j]);
				}
			}
			list.add(b1);
			list.add(b2);
			list.add(b3);
			list.add(b4);
			list.add(b5);
			list.add(b6);
			
			list.add(by15[0]);
			list.add(by15[1]);
			
			list.add(by16[0]);
			list.add(by16[1]);
			byte[] bb2 = new byte[1024];
			for(int i=0;i<list.size();i++){
				bb2[i]=list.get(i);
			}
			
//			for(int i=0;i<bb3.length;i++){
//				bb2[i]=bb3[i];
//			}
			
			
			
            InetAddress address = InetAddress.getByName(netAddress);
            datagramPacket = new DatagramPacket(bb2, bb2.length, address, PORT);
            datagramSocket.send(datagramPacket);
           
//            byte[] receBuf = new byte[1024];
//            DatagramPacket recePacket = new DatagramPacket(receBuf, receBuf.length);
//            datagramSocket.receive(recePacket);
//           
//            String receStr = new String(recePacket.getData(), 0 , recePacket.getLength());
//            InetAddress serverIp=recePacket.getAddress();
            
//            String serverIp = recePacket.getAddress();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭socket
            if(datagramSocket != null){
                datagramSocket.close();
            }
        }
    }
    
    public static void main(String[] args) {
        UdpClient udpClient = new UdpClient();
        udpClient.UdpClient2();
    }
}


