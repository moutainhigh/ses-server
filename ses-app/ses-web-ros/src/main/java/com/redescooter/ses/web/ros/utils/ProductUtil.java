package com.redescooter.ses.web.ros.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 24/10/2019 11:04 上午
 * @ClassName: ProductUtil
 * @Function: TODO
 */
public class ProductUtil {
    /**
     * Batch Number批次号：REBN+年（后2位数，例如19）月日时分秒（10位数）+2位随机数；18位；
     *
     * @return
     */
    public static String getProductBatchNumber() {
        StringBuffer sb = new StringBuffer();
        sb.append("BN").append(new SimpleDateFormat("yyMMddHHmmssSSS").format(new Date())).append(getRandomLengthStr(3));
        return String.valueOf(sb);
    }

    /**
     * 生成产品定义code
     * Product Code产品编码：RE+（整车：2W，电池：BT，配件：CP）+（规格：00～99）+（颜色：00～99）+年（显示后2位数，例如19）+（月日时分秒10位数）；20位
     *
     * @param type
     * @param model
     * @param colour
     * @return
     */
    public static synchronized String getProductCode(String type, String model, String colour) {
        StringBuffer sb = new StringBuffer();
        sb.append("RE").append(type).append(model).append(colour).append(new SimpleDateFormat("yyMMddHHmmssSSS").format(new Date()));
        return String.valueOf(sb);
    }

    /**
     * 获取随机位数字符串
     */
    public static synchronized String getRandomLengthStr(int length) {
        //产生随机数
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        //循环length次
        for (int i = 0; i < length; i++) {
            //产生0-2个随机数，既与a-z，A-Z，0-9三种可能
            int number = random.nextInt(3);
            long result = 0;
            switch (number) {
                //如果number产生的是数字0；
                case 0:
                    //产生A-Z的ASCII码
                    result = Math.round(Math.random() * 25 + 65);
                    //将ASCII码转换成字符
                    sb.append(String.valueOf((char) result));
                    break;
                case 1:
                    //产生a-z的ASCII码
                    result = Math.round(Math.random() * 25 + 97);
                    sb.append(String.valueOf((char) result));
                    break;
                case 2:
                    //产生0-9的数字
                    sb.append(String.valueOf
                            (new Random().nextInt(10)));
                    break;
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        for (int i = 10; i > 0; i--) {
            System.out.println(getProductBatchNumber());
        }
    }
}
