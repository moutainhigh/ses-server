package com.redescooter.ses.web.ros.enums.sellsy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum SellsyBriefcaseModelEnums {

    flat("flat", "平面"), files("files", "文件"), folders("folders", "文件夹");

    private String code;

    private String message;
}
