package com.redescooter.ses.mobile.rps.vo.scooterqc;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;



@ApiModel(value = "部件列表具体质检选项入参", description = "部件列表具体质检选项入参")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class ScooterQcItemOptionEnter extends GeneralEnter {

    @ApiModelProperty(value = "主键",required = true)
    private Long id;

    @ApiModelProperty(value = "质检项id")
    private String qcId;

    @ApiModelProperty(value = "组装单id")
    private Long scooterId;

    @ApiModelProperty(value = "部件id")
    private Long partId;

    @ApiModelProperty(value = "质检状态(1、质检通过/2、维修/3、更换)")
    private Integer qcStatus;


}
