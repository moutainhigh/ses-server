package com.redescooter.ses.admin.dev.exception;

/**
 * Spring-Valid注解全局异常码
 * @description: ValidationExceptionCode
 * @author: Darren
 * @create: 2019/01/17 10:26
 */
public interface ValidationExceptionCode {

    /**
     * 10000-10030 公共异常
     */
    // 平板序列号不能为空
    int TABLET_SN_IS_NOT_EMPTY = 10001;
    // 分组不能为空
    int GROUP_IS_NOT_EMPTY = 10002;
    // 颜色不能为空
    int COLOR_IS_NOT_EMPTY = 10003;
    // 蓝牙名称不能为空
    int MAC_NAME_IS_NOT_EMPTY = 10004;
    // 蓝牙地址不能为空
    int MAC_ADDRESS_IS_NOT_EMPTY = 10005;
    // 车辆配件不能为空
    int SCOOTER_PARTS_IS_NOT_EMPTY = 10006;

}
