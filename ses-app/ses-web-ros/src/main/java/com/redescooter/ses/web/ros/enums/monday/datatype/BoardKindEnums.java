package com.redescooter.ses.web.ros.enums.monday.datatype;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName:BoardKindEnums
 * @description: BoardKindEnums
 * @author: Alex
 * @Version：1.3
 * @create: 2020/08/03 15:48
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum BoardKindEnums {

    PUBLIC("public","公共的"),
    PRIVATE("private","私有的"),
    SHARE("share","可分享的");


    private String code;

    private String message;
}
