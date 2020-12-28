package com.redescooter.ses.web.ros.enums.assign;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum NodeEnum {

    VIN_CODE(1),

    BIND_LICENSE_PLATE(2),

    BIND_RSN(3);

    private Integer code;

}
