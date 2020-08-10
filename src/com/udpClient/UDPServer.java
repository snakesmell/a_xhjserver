package com.udpClient;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import com.udpUtil.CommandHex;
import com.udpUtil.MqHelper;
import com.udpUtil.TimeUtil;
import com.upttest.SchedulingPlan;
import com.upttest.TimeInterval;

/*
 * 服务器端，实现基于UDP的用户登陆
 */
public class UDPServer {
    public static void main(String[] args) throws IOException {
        /*
         * 接收客户端发送的数据
         */
        // 1.创建服务器端DatagramSocket，指定端口
        DatagramSocket socket = new DatagramSocket(9999);
        // 2.创建数据报，用于接收客户端发送的数据
        byte[] data = new byte[1024];// 创建字节数组，指定接收的数据包的大小
        DatagramPacket packet = new DatagramPacket(data, data.length);
        // 3.接收客户端发送的数据
        System.out.println("****服务器端已经启动，等待客户端发送数据");
        
        MqHelper mq = new MqHelper("admin","admin","tcp://192.168.10.224:61616");
        String topic="ServerXHJ";
        
        while(true){
	        try {
				socket.receive(packet);// 此方法在接收到数据报之前会一直阻塞
				// 4.读取数据
				String bth = byteToHex(data); 
				mq.sendTopicMessage(topic, bth);
				String receiveName="";
				/*
				 * 向客户端响应数据
				 */
				// 1.定义客户端的地址、端口号、数据
				InetAddress address = packet.getAddress();
				int port = packet.getPort();
				String xhj=address.getHostAddress()+":"+port;
				System.out.println(xhj);
				/////////////////////////////////////////信号机查询响应、应答/////////////////////////////////////////////
				// 81 01 - 4.1.1信号机联机请求
				if((data[8]==(byte) (0x81 & 0xff))&&(data[9]==(byte) (0x01 & 0xff))) {
					System.out.println("NET write:信号机联机请求-1:"+bth);
					//84 01 - 4.1.2 上位机应答
					String command="c0 10 20 10 01 00 01 00 84 01 01 00 00 00 00 78 7a 32 31 30 30 00 c0"; System.out.println("datehart:上位机联机应答-1:"+command);
					byte[] response = CommandHex.hexStringToByte(command);
					DatagramPacket packet2 = new DatagramPacket(response, response.length, address, 17899);
					socket.send(packet2);
					continue;
				}
				
				// 80 01 - 4.1.3信号机联机查询指令
				if((data[8]==(byte) (0x80 & 0xff))&&(data[9]==(byte) (0x01 & 0xff))) {
					System.out.println("NET write:信号机联机查询-2:"+bth);
					//83 01 - 4.1.4 联机查询应答
					String command="c0 10 20 10 01 00 01 00 83 01 01 00 00 00 00 78 7a 32 31 30 30 00 c0"; System.out.println("datehart :上位机联机查询应答-2:"+command);
					byte[] response = CommandHex.hexStringToByte(command);
					DatagramPacket packet2 = new DatagramPacket(response, response.length, address, 17899);
					socket.send(packet2);
					continue;
				}
				///////////////////////////////////////信号机查询响应、应答///////////////////////////////////////////////
				/////////////////////////////////////////////主动上传////////////////////////////////////////////////
				//82 02 4.2交通流信息主动上传
				if((data[8]==(byte) (0x82 & 0xff))&&(data[9]==(byte) (0x02 & 0xff))) {
					receiveName="交通流信息主动上传";
					System.out.println("NET write:"+receiveName+":"+bth);
					continue;
				}
				//82 03 4.3.3 信号机工作状态主动上传
				if((data[8]==(byte) (0x82 & 0xff))&&(data[9]==(byte) (0x03 & 0xff))) {
					receiveName="工作状态上传";
					System.out.println("NET write:"+receiveName+":"+bth);
					continue;
				}
				//82 04 4.4.3 灯色状态主动上传
				if((data[8]==(byte) (0x82 & 0xff))&&(data[9]==(byte) (0x04 & 0xff))) {
					receiveName="灯色状态主动上传";
					System.out.println("NET write:"+receiveName+":"+bth);
					
					String temp = Integer.toHexString(Integer.parseInt(String.valueOf( data[15] & 0xff )));
					
					String bits = byteToBitStr(data[15]);
					System.out.println(bits);
					signLight(bits);
					continue;
				}
				/////////////////////////////////////////////主动上传////////////////////////////////////////////////
				
				//param1
				//工作状态查询
				if((data[8]==(byte) (0x83 & 0xff))&&(data[9]==(byte) (0x03 & 0xff))) {
					receiveName="工作状态查询";
					System.out.println("NET write:"+receiveName+":"+bth);
					continue;
				}
				//param2
				//灯色状态查询
				if((data[8]==(byte) (0x83 & 0xff))&&(data[9]==(byte) (0x04 & 0xff))) {
					receiveName="灯色状态查询";
					System.out.println("NET write:"+receiveName+":"+bth);
					String temp = Integer.toHexString(Integer.parseInt(String.valueOf( data[15] & 0xff )));
					
					String bits = byteToBitStr(data[15]);
					System.out.println(bits);
					signLight(bits);
					continue;
				}
				//param3
				//83 05 4.5.2时间查询应答
				if((data[8]==(byte) (0x83 & 0xff))&&(data[9]==(byte) (0x05 & 0xff))) {
					receiveName="当前信号机时间";
					System.out.println("NET write:"+receiveName+":"+bth);
					String time=TimeUtil.transforDate(bth);
					System.out.println("当前信号机时间："+time);
					continue;
				}
				//param4
				// 灯组查询
				if((data[8]==(byte) (0x83 & 0xff))&&(data[9]==(byte) (0x06 & 0xff))) {
					receiveName="当前信号机灯组";
					System.out.println("NET write:"+receiveName+":"+bth);
					continue;
				}
				//param5
				// 相位查询
				if((data[8]==(byte) (0x83 & 0xff))&&(data[9]==(byte) (0x07 & 0xff))) {
					receiveName="当前信号相位";
					System.out.println("NET write:"+receiveName+":"+bth);
					continue;
				}
				//param6
				// 配时查询
				if((data[8]==(byte) (0x83 & 0xff))&&(data[9]==(byte) (0x08 & 0xff))) {
					receiveName="当前信号配时";
					System.out.println("NET write:"+receiveName+":"+bth);
					continue;
				}
				//param7
				//83 09 4.9 时间调度计划
				if((data[8]==(byte) (0x83 & 0xff))&&(data[9]==(byte) (0x09 & 0xff))) {
					receiveName="时间调度计划";
					System.out.println("NET write:"+receiveName+":"+bth);
					SchedulingPlan.schedulingPlan(bth);
					continue;
				}
				//param8
				//故障查询
				if((data[8]==(byte) (0x83 & 0xff))&&(data[9]==(byte) (0x0b & 0xff))) {
					receiveName="故障查询";
					System.out.println("NET write:"+receiveName+":"+bth);
					continue;
				}

				//param9
				//信号机版本查询 
				if((data[8]==(byte) (0x83 & 0xff))&&(data[9]==(byte) (0x0c & 0xff))) {
					receiveName="信号机版本查询";
					System.out.println("NET write:"+receiveName+":"+bth);
					continue;
				}
				//param10
				//特征版本查询
				if((data[8]==(byte) (0x83 & 0xff))&&(data[9]==(byte) (0x0d & 0xff))) {
					receiveName="特征版本查询";
					System.out.println("NET write:"+receiveName+":"+bth);
					continue;
				}
				//param11
				//识别码查询
				if((data[8]==(byte) (0x83 & 0xff))&&(data[9]==(byte) (0x0e & 0xff))) {
					receiveName="识别码查询";
					System.out.println("NET write:"+receiveName+":"+bth);
					continue;
				}
				//param12
				//检测器查询
				if((data[8]==(byte) (0x83 & 0xff))&&(data[9]==(byte) (0x10 & 0xff))) {
					receiveName="检测器查询";
					System.out.println("NET write:"+receiveName+":"+bth);
					continue;
				}
				//param13
				//相序表查询
				if((data[8]==(byte) (0x83 & 0xff))&&(data[9]==(byte) (0x20 & 0xff))) {
					receiveName="相序表查询";
					System.out.println("NET write:"+receiveName+":"+bth);
					continue;
				}
				//param14
				//方案表查询
				if((data[8]==(byte) (0x83 & 0xff))&&(data[9]==(byte) (0x21 & 0xff))) {
					receiveName="方案表查询";
					System.out.println("NET write:"+receiveName+":"+bth);
					continue;
				}
				//param15
				//动作查询
				if((data[8]==(byte) (0x83 & 0xff))&&(data[9]==(byte) (0x22 & 0xff))) {
					receiveName="动作查询";
					System.out.println("NET write:"+receiveName+":"+bth);
					continue;
				}
				//param16
				//时段查询
				if((data[8]==(byte) (0x83 & 0xff))&&(data[9]==(byte) (0x23 & 0xff))) {
					receiveName="时段查询";
					System.out.println("NET write:"+receiveName+":"+bth);
					TimeInterval.query(bth);
					continue;
				}
				//param17
				//跟随相位查询
				if((data[8]==(byte) (0x83 & 0xff))&&(data[9]==(byte) (0x24 & 0xff))) {
					receiveName="跟随相位查询";
					System.out.println("NET write:"+receiveName+":"+bth);
					continue;
				}
				//param18
				//单元参数查询
				if((data[8]==(byte) (0x83 & 0xff))&&(data[9]==(byte) (0x25 & 0xff))) {
					receiveName="单元参数查询";
					System.out.println("NET write:"+receiveName+":"+bth);
					continue;
				}
				//param19
				//工作方式查询
				if((data[8]==(byte) (0x83 & 0xff))&&(data[9]==(byte) (0x0a & 0xff))) {
					receiveName="工作方式查询";
					System.out.println("NET write:"+receiveName+":"+bth);
					continue;
				}
				
				receiveName="其它";
				//其它
				System.out.println("NET write:"+receiveName+":"+bth);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				 // 4.关闭资源
//		        socket.close();
			}
        }
       
    }
    
    
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
                
                if(i>0&&tmp==192)break;//末位跳出
            }
            return sb.toString().toUpperCase();
    }
    //字节转bit
    public static String byteToBitStr(byte by) {
	    StringBuffer sb = new StringBuffer();
	    //每一位与 000000001按位与运算。保证每一位是 0或者1
	    sb.append((by>>7)&0x1);
	    sb.append((by>>6)&0x1);
	    sb.append((by>>5)&0x1);
	    sb.append((by>>4)&0x1);
	    sb.append((by>>3)&0x1);
	    sb.append((by>>2)&0x1);
	    sb.append((by>>1)&0x1);
	    sb.append((by>>0)&0x1);
	    return sb.toString();
	}
    //灯色
    public static void signLight(String temp) {
		String t1=temp.substring(0, 2);
		String t2=temp.substring(2, 4);
		String t3=temp.substring(4, 6);
		String t4=temp.substring(6, 8);
		
		String []a1= {"00","01","10","11"};
		String []b1= {"不亮","红","黄","绿"};
		for(int i=0;i<4;i++) {
			if(a1[i].equals(t1)) { System.out.print(" 1-"+b1[i]); }
		}
		for(int i=0;i<4;i++) {
			if(a1[i].equals(t2)) { System.out.print(" 2-"+b1[i]); }
		}
		for(int i=0;i<4;i++) {
			if(a1[i].equals(t3)) { System.out.print(" 3-"+b1[i]); }
		}
		for(int i=0;i<4;i++) {
			if(a1[i].equals(t4)) { System.out.println(" 4-"+b1[i]); }
		}
		
	}
}
