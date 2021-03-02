package com.redescooter.ses.tool.utils.chart;

import java.text.NumberFormat;

/**
 * @description: StatisticalUtil
 * @author: Alex
 * @create: 2019/02/20 18:47
 */
public class StatisticalUtil {
	/**
	 *给定两个值 返回百分比
	 * @param molecular 除数
	 * @param denominator 被除数
	 * @param digit 需要保留的位数
	 * @return
	 */
	public static String percentageUtil(int molecular, int denominator, int digit) {
		NumberFormat numberFormat = NumberFormat.getInstance();
		numberFormat.setMaximumFractionDigits(digit);
		String completeProportion =
				numberFormat.format((float) molecular / (float) denominator * 100);
		return completeProportion;
	}

	public static void main(String[] args) {
		System.out.println(percentageUtil(10, 30, 0));
	}
}
