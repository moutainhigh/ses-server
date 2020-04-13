package com.redescooter.ses.web.ros.dm;

import com.fasterxml.jackson.databind.ser.std.SerializableSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @ClassName:PartDetailDao
 * @description: PartDetailDao
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/24 15:04
 */
@ApiModel(value = "部件价格信息", description = "部件价格信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class PartDetailDto extends SerializableSerializer {

    @ApiModelProperty(value = "部件Id")
    private Long partId;

    @ApiModelProperty(value = "价格")
    private BigDecimal price;

    @ApiModelProperty(value = "单位")
    private String unit;

    @ApiModelProperty(value = "供应商Id")
    private Long supplierId;
}
