package com.redescooter.ses.web.ros.enums.assign;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum FlagEnum {

    NOT(1),

    YES(2);

    private Integer code;

}
