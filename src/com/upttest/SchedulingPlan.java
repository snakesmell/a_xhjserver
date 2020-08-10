package com.upttest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.udpUtil.CommandHex;

public class SchedulingPlan {

	private static String []month= {"占位符","一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"};
	private static String []week= {"占位符","星期日","星期一","星期二","星期三","星期四","星期五","星期六"};
	private static String []day= {"占位符","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"};
	
	/**
	 * 调度计划时间
	 * @param bth
	 */
	public static void schedulingPlan(String bth) {
//		String bth="C0 10 10 20 03 00 01 00 83 09 31 31 31 31 31 01 01 FC 0F FE FA FF FF 7F 01 00 00 00 00 C0";
		bth=bth.trim();
		String[] sp = bth.split(" ");
		List<String> list=new ArrayList();
		list.addAll(reverse(sp[17]));
		list.addAll(reverse(sp[18]));
//		System.out.println(list);//月份，从下标1开始为1月
		
		String res=transforChinese(month,list);
		System.out.println(res);
		
		list=new ArrayList();
		list=reverse(sp[19]);
//		System.out.println(list);//星期，从下标1开始，日，一二三四五六
		
		res=transforChinese(week,list);
		System.out.println(res);
		
		list=new ArrayList();
		list.addAll(reverse(sp[20]));
		list.addAll(reverse(sp[21]));
		list.addAll(reverse(sp[22]));
		list.addAll(reverse(sp[23]));
//		System.out.println(list);//日期，从下标1开始，1,2,3,4...31
		
		res=transforChinese(day,list);
		System.out.println(res);
	}
	/**
	 * 转换中文
	 * @param date
	 * @param list
	 * @return
	 */
	public static String transforChinese(String []date,List<String> list) {
		StringBuilder sb=new StringBuilder();
		for(int i=0;i<list.size();i++) {
			String flag=list.get(i);
			if(flag.equals("1")) {
				sb.append("-");
				sb.append(date[i]);
			}
		}
		return sb.toString();
	}
	
	/**
	 * 二进制位转换，序列倒转
	 * @param sp
	 * @return
	 */
	public static List<String> reverse(String sp){
		int temp=CommandHex.hexTool(sp);//转成16进制整数
		byte bt2=(byte) (temp&0xff);//二进制转换
		String result=byteToBitStr(bt2);//转成8位bit
		char[] arry = result.toCharArray();
		List<String> list2=new ArrayList();
		for(int i=0;i<arry.length;i++) {
			list2.add(String.valueOf(arry[i]));
		}
		Collections.reverse(list2);
		return list2;
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
    
}
