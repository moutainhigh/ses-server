package com.redescooter.ses.api.mobile.c.vo;

import java.math.BigDecimal;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @ClassName:SaveDriverRideDateEnter
 * @description: SaveDriverRideDateEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/02/20 16:11
 */
@ApiModel(value = "保存司机骑行数据入参", description = "保存司机骑行数据入参")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class SaveRideDateEnter extends GeneralEnter {

    @ApiModelProperty(value = "距离")
    private BigDecimal mileage;

    @ApiModelProperty(value = "耗时")
    private Long duration;

    @ApiModelProperty(value = "bizType")
    private String bizType;

    @ApiModelProperty(value = "bizId")
    private Long bizId;
}
