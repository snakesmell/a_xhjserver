package com.util;

public class Test {
	
	public static void main(String[] args) {
		
		 byte []b=new byte[1024];
		 String command="c0 10 20 10 01 00 01 00 84 01 00 00 00 00 00 78 7a 32 31 30 30 00 c0";
		 
		 String[] sp = command.split(" ");
		 for(int i=0;i<sp.length;i++) {
			 sp[i]=sp[i];
			 byte[] xx = hexStringToByte(sp[i]);
			 System.out.println(xx[0]);
			 b[i]=xx[0];
		 }
	}
	//方法调用
	public byte [] commoand(String command) {
		 byte []b=new byte[1024];
		 String[] sp = command.split(" ");
		 for(int i=0;i<sp.length;i++) {
			 sp[i]=sp[i];
			 byte[] xx = hexStringToByte(sp[i]);
			 System.out.println(xx[0]);
			 b[i]=xx[0];
		 }
		 return b;
	}
	//Step1
    public static byte[] hexStringToByte(String hex) {
    	  int len = (hex.length() / 2);
    	  byte[] result = new byte[len];
    	  char[] achar = hex.toCharArray();
    	  for (int i = 0; i < len; i++) {
    	       int pos = i * 2;
    	       result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
    	  }
    	  return result;
    }
    //Step2
    private static byte toByte(char c) {
          byte b = (byte) "0123456789abcdef".indexOf(c);
          return b;
    }
}

/**

//1.帧开始
responseBytes[0]=hexToByte("c0")[0];
//2.版本号
responseBytes[1]=hexToByte("10")[0];
//3.发送方标识
responseBytes[2]=hexToByte("20")[0];
//4.接收方标识
responseBytes[3]=hexToByte("10")[0];
//5.数据链路码
responseBytes[4]=hexToByte("01")[0];
//6.区域号
responseBytes[5]=hexToByte("00")[0];
//7.路口号
responseBytes[6]=hexToByte("01")[0];
responseBytes[7]=hexToByte("00")[0];
//8.操作类型
responseBytes[8]=hexToByte("84")[0];
//9.对象标识
responseBytes[9]=hexToByte("01")[0];
//10.保留
responseBytes[11]=hexToByte("01")[0];
//11.数据内容
responseBytes[12]=hexToByte("01")[0];
responseBytes[13]=hexToByte("01")[0];
responseBytes[14]=hexToByte("01")[0];
responseBytes[15]=hexToByte("01")[0];

StringToHex strToHex = new StringToHex();
String hex = strToHex.convertStringToHex("XZ2100");
byte[] temp = hexToByte(hex);

responseBytes[16]=temp[0];
responseBytes[17]=temp[1];
responseBytes[18]=temp[2];
responseBytes[19]=temp[3];
responseBytes[20]=temp[4];
responseBytes[21]=temp[5];

//12.帧校验
responseBytes[22]=hexToByte("00")[0];
//13.帧结束
responseBytes[23]=hexToByte("c0")[0];

**/

