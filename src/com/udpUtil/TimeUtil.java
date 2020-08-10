package com.udpUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TimeUtil {
	
	/**
	 * 4字节。当前具体格林尼治时间1970年1月1日零时以来的秒数 
	 * @param bth
	 */
	public static String transforDate(String bth){
		bth=bth.trim();
		String[] sp = bth.split(" ");
		
		int a=CommandHex.hexTool(sp[15]);		 
		int b=CommandHex.hexTool(sp[16]);
		int c=CommandHex.hexTool(sp[17]);
		int d=CommandHex.hexTool(sp[18]);
		
		int sum=a+b*256+c*256*256+d*256*256*256;
		
		Calendar cal = Calendar.getInstance();
//		中国北京时间与格林尼治标准时间相差8小时。
		cal.set(1970, 01-1, 01,8,0,0);
		cal.add(Calendar.SECOND, sum);
		return getTimeStamp(cal);
	}
	 //精确到年月日（英文） eg:2019-12-31
    public static String FORMAT_LONOGRAM = "yyyy-MM-dd";
    //精确到时分秒的完整时间（英文） eg:2010-11-11 12:12:12
    public static String FORMAT_FULL = "yyyy-MM-dd HH:mm:ss";
    //精确到毫秒完整时间（英文） eg:2019-11-11 12:12:12.55
    public static String FORMAT_LONOGRAM_MILL = "yyyy-MM-dd HH:mm:ss.SSS";
    //精确到年月日（中文）eg:2019年11月11日
    public static String FORMAT_LONOGRAM_CN = "yyyy年MM月dd日";
    //精确到时分秒的完整时间（中文）eg:2019年11月11日 12时12分12秒
    public static String FORMAT_FULL_CN = "yyyy年MM月dd日 HH时MM分SS秒";
	  /**
     * 获取时间戳 eg:yyyy-MM-dd HH:mm:ss.S
     * @return
     */
    public static String getTimeStamp(Calendar cal) {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_FULL);
        return sdf.format(cal.getTime());
    }
    //正确代码
    public static String byteToHex(byte t){
    		byte []bt=new byte[1];
    		bt[0]=t;
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
}
