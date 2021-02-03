package com.redescooter.ses.web.ros.utils;

import com.google.common.base.Strings;
import com.redescooter.ses.tool.utils.date.DateUtil;

/**
 * @ClassNameExcelUtil
 * @Description  生成单据号的util
 * @Author Aleks
 * @Date2020/7/6 11:24
 * @Version V1.0
 **/
public class OrderGenerateUtil {


    /**
     * @Author Aleks
     * @Description   生成规则 日期加三位数流水号
     * @Date  2020/11/6 15:59
     * @Param [orderNo] 单据号 例：RTO20201106001
     * @return 只返回日期加三位数流水号   前缀自己在外面拼上去
     **/
    public static String orderGenerate(String orderNo) {
        String code = "";
        if (!Strings.isNullOrEmpty(orderNo)) {
            String oldCode = orderNo;
            Integer i = Integer.parseInt(oldCode.substring(oldCode.length() - 3));
            i++;
            code = DateUtil.getSimpleDateStamp() + String.format("%3d", i).replace(" ", "0");
        }
        return code;
    }

    public static void main(String[] args) {
        String number = orderGenerate("RPO20201109002");
        System.out.println(number);
    }

}
