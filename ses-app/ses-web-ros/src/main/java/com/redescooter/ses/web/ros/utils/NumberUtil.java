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

    public static int getStrToNum(String str) {
        int num = 0;
        if (StringUtils.isBlank(str)) {
            return num;
        }

        try {
            if (!str.contains(".")) {
                num = Integer.parseInt(str);
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
}
