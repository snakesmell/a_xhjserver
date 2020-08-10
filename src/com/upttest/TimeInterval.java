package com.upttest;

import com.udpUtil.CommandHex;

public class TimeInterval {

	/**
	 * 查询时段配置
	 * @param bth
	 */
	public static void query(String bth) {
		bth=bth.trim();
//		String bth="C0 10 10 20 03 00 01 00 83 23 31 31 31 31 31 02 01 01 0B 15 01 01 00 00 00 00 00 00 01 02 10 00 02 FF 00 00 00 00 00 00 00 C0";
		String[] sp = bth.split(" ");
		//15  //12
		int num=CommandHex.hexTool(sp[15]);
		for(int i=0;i<num;i++) {
			String row=sp[15+2+(i*12)];
			int hour=CommandHex.hexTool(sp[15+3+(i*12)]);//小时
			int minute=CommandHex.hexTool(sp[15+4+(i*12)]);//分钟
			int action=CommandHex.hexTool(sp[15+5+(i*12)]);//动作
			int plan=CommandHex.hexTool(sp[15+6+(i*12)]);//方案
			System.out.println("row:"+row+" 时间： "+hour+":"+minute+" 动作："+action+" 方案："+plan );
		}
	}
	
}
