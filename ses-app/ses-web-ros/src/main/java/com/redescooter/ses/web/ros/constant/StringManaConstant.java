package com.redescooter.ses.web.ros.constant;

/**
 * @ClassName StringManaConstant
 * @Description 魔法值
 * @Author Charles
 * @Date 2021/05/26 20:17
 */
public class StringManaConstant {


    /**
     * 国内时区
     */
    public static final String GMT_TIME_ZONE = "+08:00";

    /**************************** Response parameter start **************************/

    public static final String RESPONSE_STATUS_SUCESS = "sucess";

    public static final String RESPONSE_ACK = "ack";

    public static final String RESPONSE_TOTAL = "total";

    public static final String RESPONSE_DATA = "data";
    /**************************** Response parameter  end  **************************/

    /**************************** SQL start  **************************/
    /**
     * 查询一条
     */
    public static final String SQL_LAST_LIMIT_ONE = "limit 1";

    /**************************** SQL  end   **************************/


    /**
     * 值为null，返回true
     * @param obj
     * @return
     */
    public static boolean entityIsNull(Object obj) {
        if (null == obj) {
            return true;
        }
        return false;
    }

    /**
     * 值为null，返回false
     * @param obj
     * @return
     */
    public static boolean entityIsNotNull(Object obj) {
        if (null != obj) {
            return true;
        }
        return false;
    }

}
