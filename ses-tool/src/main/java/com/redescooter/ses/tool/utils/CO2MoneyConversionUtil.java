package com.redescooter.ses.tool.utils;

import java.text.DecimalFormat;

/**
 * @description: CO2MoneyConversionUtil
 * @author: Alex
 * @create: 2019/02/15 16:16
 */
public class CO2MoneyConversionUtil {

    //每米产生的Co2
    private static double cO2Proportion;
    //每米节省的钱
    private static double savingMoney;

    /**
     * CO2转换
     *
     * @param distance
     * @return
     */
    public static String cO2Conversion(long distance) {

        String zero = "0.00";
        //CO2比例 没 m 产生 co2
        cO2Proportion = 0.000071;
        DecimalFormat df = new DecimalFormat("#0.00");
        String CO2 = df.format(distance * cO2Proportion);
        if (Double.parseDouble(CO2) > 0) {
            return CO2;
        }
        return zero;
    }

    /**
     * SavingMoney转换
     *
     * @param distance
     * @return
     */
    public static String savingMoneyConversion(long distance) {
        String zero = "0.00";
        DecimalFormat df = new DecimalFormat("#.00");
        //每m节省的钱
        savingMoney = 0.000045;
        String money = df.format(distance * savingMoney);
        if (Double.parseDouble(money) > 0) {
            return money;
        }
        return zero;

    }

    /**
     * 树等级转换
     *
     * @param co2
     * @return
     */
    public static int treeConversion(Double co2) {

        return (int) ArithUtils.div(co2, 5000);
    }

    /**
     * 车辆转换
     *
     * @param co2
     * @return
     */
    public static Integer vehicleConversion(Double co2) {
        return (int) ArithUtils.div(co2, 500);
    }

}
