package com.redescooter.ses.web.ros.vo.website;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;

import java.math.BigDecimal;

/**
 * @ClassName:ScooterBatteryResult
 * @description: ScooterBatteryResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/05/12 16:24
 */
@ApiModel(value = "电池配件出参", description = "电池配件出参")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class AccessoryResult extends GeneralResult {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "配件电池类型")
    private String accessoryType;

    @ApiModelProperty(value = "配件电池名称")
    private String accessoryName;

    @ApiModelProperty(value = "配件电池价格")
    private BigDecimal price;
}
