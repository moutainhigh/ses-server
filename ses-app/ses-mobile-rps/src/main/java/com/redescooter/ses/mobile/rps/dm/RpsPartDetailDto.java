package com.redescooter.ses.mobile.rps.dm;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @ClassName:PartDetailDto
 * @description: PartDetailDto
 * @author: Alex
 * @Version：1.3
 * @create: 2020/04/15 16:11
 */
@ApiModel(value = "部件基本信息", description = "部件基本信息")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class RpsPartDetailDto extends GeneralEnter {

    @ApiModelProperty(value = "部件Id")
    private Long partId;

    @ApiModelProperty(value = "价格")
    private BigDecimal price;

    @ApiModelProperty(value = "单位")
    private String unit;

    @ApiModelProperty(value = "供应商Id")
    private Long supplierId;
}
