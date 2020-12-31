package com.redescooter.ses.web.ros.enums.distributor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum DelStatusEnum {

    VALID(0, "正常"),

    INVALID(1, "已刪除");

    private Integer code;

    private String msg;

}
