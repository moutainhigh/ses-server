package com.redescooter.ses.api.common.enums.base;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 30/12/2019 4:30 下午
 * @ClassName: ValidateCodeEnums
 * @Function: TODO
 */
@Getter
@AllArgsConstructor
public enum ValidateCodeEnums {

    LOGIN("LOGIN", 1, "登录"), ACTIVAT("ACTIVAT", 2, "激活"), RESET_PASSWORD("RESET_PASSWORD", 3, "密码重置"),;

    // 业务编码
    private String code;

    // 编码对应值
    private Integer value;

    // 编码备注说明
    private String remark;

}
