package com.redescooter.ses.mobile.rps.vo.scooterqc;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import lombok.*;

import io.swagger.annotations.*;

@ApiModel(value = "整车部件质检项具体选项处理", description = "整车部件质检项具体选项处理")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class ScooterQcItemEnter extends GeneralEnter {

    @ApiModelProperty(value = "组装单子单id")//调整
    private Long scooterBId;

    @ApiModelProperty(value = "产品id")//调整
    private Long partId;

    @ApiModelProperty(value = "质检项信息集合")
    private String scooterQcItemOptionEnter;

    @ApiModelProperty(value = "待质检部品数量")
    private Integer partNum;




}
