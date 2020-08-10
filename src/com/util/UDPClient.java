package com.util;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class UDPClient {
	public static DatagramSocket service;
	public static final int MAX_BYTES = 24;
	
    public static void main(String[] args){
    	
//    	byte[] x = "c0".getBytes();
//    	System.out.println(x.length);
    	
//    	Byte b = new Byte("c0 10 20 10 01 00 01 00 84 01 00 00 00 00 00 78 7a 32 31 30 30 00 c0");
//    	System.out.println(b);
    	
    	byte b=(byte) (0XC0 & 0XFF);
    	short c=(short) (b & 0xff);
    	System.out.println(b+"-"+c);
    	
//    	for (;;) {
//			
//    	Scanner in = new Scanner(System.in);  
//        String hex_num = in.nextLine();  
//        long dec_num = Long.parseLong(hex_num, 16);  
//        System.out.println(dec_num);  
//    	}

    	
    }
//    c0 10 10 20 01 00 01 00 80 01 01 01 01 01 01 00 c0 
    public void toBinary(String str){
        char[] strChar=str.toCharArray();
        String result="";
        for(int i=0;i<strChar.length;i++){
            result +=Integer.toBinaryString(strChar[i])+ " ";
        }
        System.out.println(result);
    }
    
    public static void sendAndReceive(String ip, int port,byte[] responseBytes) {
	    try{
	    	byte[] s = new byte[22];
	    	
	    	
	    	
//	    	c0 10 20 10 01 00 01 00 84 01 00 00 00 00 00 78 7a 32 31 30 30 00 c0 
	    	InetAddress  inetAddress = InetAddress.getByName("192.168.10.241");
			port = 7755;
	   	    //包装IP地址
	        //创建服务端的DatagramSocket对象，需要传入地址和端口号
	        service = new DatagramSocket();
		    //创建响应信息的包对象，由于要发送到目的地址，所以要加上目的主机的地址和端口号
		    DatagramPacket responsePacket = new DatagramPacket(responseBytes,responseBytes.length,inetAddress,port);
	          //发送数据
	        service.send(responsePacket);
	    }catch (Exception e){
	        e.printStackTrace();
	    }finally {
            //关闭DatagramSocket对象
            if(service!=null){
                service.close();
                service = null;
            }
        }

    }
    
    /**
     * 十六进制转字节
     * hex转byte数组
     * @param hex
     * @return
     */
    public static byte[] hexToByte(String hex){
        int m = 0, n = 0;
        int byteLen = hex.length() / 2; // 每两个字符描述一个字节
        byte[] ret = new byte[byteLen];
        for (int i = 0; i < byteLen; i++) {
            m = i * 2 + 1;
            n = m + 1;
            int intVal = Integer.decode("0x" + hex.substring(i * 2, m) + hex.substring(m, n));
            ret[i] = Byte.valueOf((byte)intVal);
            //System.out.println(ret[i]);
        }
        return ret;
    }
    
}