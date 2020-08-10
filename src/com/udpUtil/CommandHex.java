package com.udpUtil;

public class CommandHex {
	
	public static byte[] hexStringToByte(String command) {
//		String command="c0 10 20 10 01 00 01 00 84 01 00 00 00 00 00 78 7a 32 31 30 30 00 c0";
		String[] s = command.split(" ");
		byte []bs=new byte[s.length];
		for(int i=0;i<s.length;i++) {
			int temp=hexTool(s[i]);
			byte bt=(byte) (temp&0xff);
//			System.out.print(bt);
			bs[i]=bt;
		}
		return bs;
//		System.out.println(bs);
	}
	
	//16进制字符串转整数
	public static int hexTool(String hex) {
		
		 hex=hex.toLowerCase();//将16进制转成小写
//		 System.out.println(hex);
	     int []temp=new int[2];
	     for(int i=0;i<hex.length();i++) {
	    	 char a=hex.charAt(i);
	    	 String val=compare(a);
	    	 //16进制转10进制
    		 int p=Integer.parseInt(val);
    		 temp[i]=p;
	     }
	     
	     int sum=0;
	     for(int i=0;i<temp.length;i++) {
	    	 if(i==0) {
	    		 sum+=(temp[i]*16);
	    	 }else {
	    		 sum+=temp[i];
	    	 }
	     }
//	     System.out.println(sum);
	     return sum;
	}
	
	public static String compare(char x) {
		char []hex_name  = {'a','b','c','d','e','f'};
	    int    []hex_value = {10 , 11, 12, 13, 14, 15};
		for(int i=0;i<hex_name.length;i++) {
			if(x==hex_name[i]) {
	    		return String.valueOf(hex_value[i]); 
	    	}
		}
		return String.valueOf(x);
	}
	
	//方法调用
}
