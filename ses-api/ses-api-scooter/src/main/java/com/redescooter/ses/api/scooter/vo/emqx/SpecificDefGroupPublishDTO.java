package com.redescooter.ses.api.scooter.vo.emqx;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * 规格自定义项分组 -- 用于EMQ X指令下发时使用
 * @author assert
 * @date 2020/12/8 15:08
 */
@Data
@AllArgsConstructor
public class SpecificDefGroupPublishDTO {

    /**
     * 分组名称
     */
    private String name;

    /**
     * 规格自定义项集合(可以理解成车辆不同电池时的限制信息)
     */
    private List<SpecificDefPublishDTO> specificDefList;

}
