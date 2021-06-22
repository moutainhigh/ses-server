package com.redescooter.ses.web.ros.utils;

import com.redescooter.ses.web.ros.exception.ExceptionCodeEnums;
import com.redescooter.ses.web.ros.exception.SesWebRosException;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang.StringUtils;

/**
 * @ClassName NumberUtil
 * @Description 数字
 * @Author Charles
 * @Date 2021/05/20 13:21
 */
@Log4j
public class NumberUtil {

    static final Integer NUM_ZERO = 0;

    static final Integer NUM_ONE = 1;

    static final Integer NUM_SIX = 6;

    static final Integer NUM_TWO = 2;

    static final Integer NUM_EIGHT = 8;

    static final Integer NUM_TEN = 10;

    static final Integer NUM_TWENTY = 20;

    static final Integer NUM_THIRTY = 30;

    static final Integer NUM_FORTY = 40;

    static final Integer NUM_FIFTY = 50;

    static final Integer NUM_TWO_HUNDRED = 200;


    public static int getStrToNum(String str) {
        int num = 0;
        if (StringUtils.isBlank(str)) {
            return num;
        }

        try {
            if (!str.contains(".")) {
                return Integer.parseInt(str);
            }
            String afterNum = str.substring(str.indexOf(".") + 1);
            int anInt = Integer.parseInt(afterNum);
            if (0 < anInt) {
                throw new SesWebRosException(ExceptionCodeEnums.QUANTITY_ILLEGAL.getCode(), ExceptionCodeEnums.QUANTITY_ILLEGAL.getMessage());
            }
            String frontNum = str.substring(0, str.indexOf("."));
            if (StringUtils.isNotBlank(frontNum)) {
                num = Integer.parseInt(frontNum);
            }
        } catch (Exception e) {
            throw new SesWebRosException(ExceptionCodeEnums.QUANTITY_ILLEGAL.getCode(), ExceptionCodeEnums.QUANTITY_ILLEGAL.getMessage());
        }
        return num;
    }

    /**
     * 小于 1 或者 大于 6
     * @param num
     * @return
     */
    public static boolean ltOneOrGtSix(int num){
        if (NUM_ONE > num || NUM_SIX < num) {
            return true;
        }
        return false;
    }

    /**
     * 小于 2 或者 大于 30
     * @param num
     * @return
     */
    public static boolean ltTwoOrGtThirty(int num){
        if (NUM_TWO > num || NUM_THIRTY < num) {
            return true;
        }
        return false;
    }


    /**
     * 大于 30
     * @param num
     * @return
     */
    public static boolean GtThirty(int num){
        if (NUM_THIRTY < num) {
            return true;
        }
        return false;
    }


    /**
     * 小于8 或者 大于20
     * @param num
     * @return
     */
    public static boolean ltEightOrGtTwenty(int num) {
        if (NUM_EIGHT > num || NUM_TWENTY < num) {
            return true;
        }
        return false;
    }

    /**
     * 小于2 或者 大于 200
     * @param num
     * @return
     */
    public static boolean ltTwoOrGtTwoHundred(int num){
        if (NUM_TWO > num || NUM_TWO_HUNDRED < num) {
            return true;
        }
        return false;
    }

    /**
     * 小于2 或者 大于50
     * @param num
     * @return
     */
    public static boolean ltTwoOrGtFifty(int num) {
        if (NUM_TWO > num || NUM_FIFTY < num) {
            return true;
        }
        return false;
    }


    /**
     * 大于50
     * @param num
     * @return
     */
    public static boolean GtFifty(int num) {
        if ( NUM_FIFTY < num) {
            return true;
        }
        return false;
    }

    /**
     * 小于2 或者大于40
     * @param num
     * @return
     */
    public static boolean ltTwoOrGtForty(int num){
        if (NUM_TWO > num || NUM_FORTY < num) {
            return true;
        }
        return false;
    }

    /**
     * 小于2 或者 大于 20
     * @param num
     * @return
     */
    public static boolean ltTwoOrGtTwenty(int num){
        if (NUM_TWO > num || NUM_TWENTY < num) {
            return true;
        }
        return false;
    }

    /**
     * 小于2 或者 大于10
     * @param num
     * @return
     */
    public static boolean ltTwoOrGtTen(int num) {
        if (NUM_TWO > num || NUM_TEN < num) {
            return true;
        }
        return false;
    }

    /**
     * 不等于null 并且 大于50
     * @param param
     * @return
     */
    public static boolean notNullAndGtFifty(String param) {
        if (null != param && NUM_FIFTY < param.length()) {
            return true;
        }
        return false;
    }

    /**
     * 等于0
     * @param num
     * @return
     */
    public static boolean eqZero(int num) {
        if (NUM_ZERO == num) {
            return true;
        }
        return false;
    }

}
