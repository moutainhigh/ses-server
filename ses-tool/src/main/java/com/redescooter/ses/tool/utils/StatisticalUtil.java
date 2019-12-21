package com.redescooter.ses.tool.utils;

import java.text.NumberFormat;

/**
 * @description: StatisticalUtil
 * @author: Alex
 * @create: 2019/02/20 18:47
 */
public class StatisticalUtil {
	/**
	 *给定两个值 返回百分比
	 * @param molecular
	 * @param denominator
	 * @return
	 */
	public static String percentageUtil(int molecular,int denominator){
		NumberFormat numberFormat = NumberFormat.getInstance();
		numberFormat.setMaximumFractionDigits(2);
		String completeProportion =
				numberFormat.format((float) molecular / (float) denominator * 100);
		return completeProportion;
	}
}
