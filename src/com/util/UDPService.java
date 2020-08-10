package com.util;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import com.udpUtil.CommandHex;

public class UDPService {
    public static final String SERVICE_IP = "192.168.10.65";

    public static final int SERVICE_PORT = 9999;

    public static final int MAX_BYTES = 3096;

    private DatagramSocket service;

    public static void main(String[] args) {
        UDPService udpService = new UDPService();
        udpService.startService(SERVICE_IP,SERVICE_PORT);//启动服务端
        
        
    }
    
    /**
     * byte数组转hex
     * @param bytes
     * @return
     */
//    public static String byteToHex(byte[] bytes){
//        String strHex = "";
//        StringBuilder sb = new StringBuilder("");
//        for (int n = 0; n < bytes.length; n++) {
//            strHex = Integer.toHexString(bytes[n] & 0xFF);
//            String temp=(strHex.length() == 1) ? "0" + strHex : strHex; // 每个字节由两个字符表示，位数不够，高位补0
//            System.out.print(bytes[n]+":");
//        }
//        System.out.println();
//        return sb.toString().trim();
//    }
    
    //正确代码
    public static String byteToHex(byte[] bt){
            StringBuffer sb = new StringBuffer();
            for(int i=0;i<bt.length;i++){
                int tmp = bt[i]&0xff;
                String tmpStr = Integer.toHexString(tmp);
                if(tmpStr.length()<2)
                    sb.append("0");
                sb.append(tmpStr);
                sb.append(" ");
            }
            return sb.toString().toUpperCase();
    }
    
    private void startService(String ip, int port) {
        try {
            //包装IP地址
            InetAddress address = InetAddress.getByName(ip);
            //创建服务端的DatagramSocket对象，需要传入地址和端口号
            service = new DatagramSocket(port,address);

            byte[] receiveBytes = new byte[MAX_BYTES];
            //创建接受信息的包对象
            DatagramPacket receivePacket = new DatagramPacket(receiveBytes,receiveBytes.length);

            //开启一个死循环，不断接受数据
            while(true){
                try{
                    //接收数据，程序会阻塞到这一步，直到收到一个数据包为止
                    service.receive(receivePacket);
                }catch (Exception e){
                    e.printStackTrace();
                }
                
                byte[] data = receivePacket.getData();
                String ss = byteToHex(data);
                System.out.println(ss);
                //System.out.println(data.length);
                //for (int i = 0; i < data.length; i++) {
				//	System.out.println(data[i]);
				//}
                
                //解析收到的数据
//                String receiveMsg = new String(receivePacket.getData(),0,receivePacket.getLength());
//                System.out.println(receiveMsg);
                //解析客户端地址
//                InetAddress clientAddress = receivePacket.getAddress();
                //解析客户端端口
//                int clientPort = receivePacket.getPort();

                //组建响应信息
//                String response = "Hello world " + System.currentTimeMillis() + " " + receiveMsg;
//                byte[] responseBuf = response.getBytes();
                
                
                String command="c0 10 20 10 01 00 01 00 83 01 00 00 00 00 00 78 7a 32 31 30 30 00 c0";
                receiveBytes = CommandHex.hexStringToByte(command);
                //创建响应信息的包对象，由于要发送到目的地址，所以要加上目的主机的地址和端口号
                DatagramPacket responsePacket = new DatagramPacket(receiveBytes,receiveBytes.length,17899);

                try{
                    //发送数据
                    service.send(responsePacket);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
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
            System.out.println(ret[i]);
        }
        return ret;
    }
}
