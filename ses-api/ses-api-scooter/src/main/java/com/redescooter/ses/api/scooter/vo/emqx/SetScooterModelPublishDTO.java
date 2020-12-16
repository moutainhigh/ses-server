package com.redescooter.ses.api.scooter.vo.emqx;

import lombok.Data;

import java.util.List;

/**
 * EMQ X向车辆平板端下发设置车型消息 DTO
 * @author assert
 * @date 2020/12/1 10:59
 */
@Data
public class SetScooterModelPublishDTO {

    /**
     * 平板序列号
     */
    private String tabletSn;

    /**
     * 车辆类型 1-E25  2-E50  3-E100  4-E125
     */
    private Integer scooterModel;

    /**
     * 规格自定义项分组集合(可以理解成车辆电池集合,现在一辆车只有四块电池)
     */
    private List<SpecificDefGroupPublishDTO> specificDefGroupList;

}
