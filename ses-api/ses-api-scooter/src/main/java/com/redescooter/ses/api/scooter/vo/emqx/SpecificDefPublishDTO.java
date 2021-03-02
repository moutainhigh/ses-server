package com.redescooter.ses.api.scooter.vo.emqx;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 规格自定义项 -- 用于EMQ X指令下发时使用
 * @author assert
 * @date 2020/12/8 15:09
 */
@Data
@AllArgsConstructor
public class SpecificDefPublishDTO {

    /**
     * 自定义名称
     */
    private String defName;

    /**
     * 自定义值
     */
    private String defValue;

}
