package com.redescooter.ses.tool.utils;

import com.google.common.base.Strings;

/**
 * @ClassNameOrderNoGenerateUtil
 * @Description 单据号生成规则
 * @Author Aleks
 * @Date2020/11/10 16:07
 * @Version V1.0
 **/
public class OrderNoGenerateUtil {

    /**
     * @Author Aleks
     * @Description   生成规则 日期加三位数流水号
     * @Date  2020/11/6 15:59
     * @Param [orderNo] 单据号 例：RTO20201106001
     * @return 返回 前缀+年月日+加三位数流水号 的单据号
     **/
    public static String orderNoGenerate(String orderNo,String orderEnum) {
        String code = "";
        if (!Strings.isNullOrEmpty(orderNo)) {
            String oldCode = orderNo;
            Integer i = Integer.parseInt(oldCode.substring(oldCode.length() - 3));
            i++;
            code = DateUtil.getSimpleDateStamp() + String.format("%3d", i).replace(" ", "0");
        }else {
            // 说明今天还没有产生过单据号，给今天的第一个就好
            code = orderEnum + DateUtil.getSimpleDateStamp() + "001";
        }
        return orderEnum + code;
    }


    /**
     * @Author Aleks
     * @Description  生成6位递增的流水号 000001开始
     * @Date  2020/12/4 18:00
     * @Param [orderNo]
     * @return
     **/
    public static String createWorkOrderNo(String orderNo){
        String code = "";
        if (!Strings.isNullOrEmpty(orderNo)){
            Integer i = Integer.parseInt(orderNo);
            i++;
            code = String.format("%6d", i).replace(" ", "0");
        }else {
            code = "000001";
        }
        return code;
    }


    public static void main(String[] args) {
//        System.out.println(orderNoGenerate("RTO20201110099","RTO"));
        System.out.println(createWorkOrderNo("000009"));
    }

}
