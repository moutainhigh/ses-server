package com.redescooter.ses.web.ros.vo.qctemplete;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;

/**
 * @ClassName:SaveByCopyIdEnter
 * @description: SaveByCopyIdEnter
 * @author: Alex @Version：1.3
 * @create: 2020/10/13 17:46
 */
@ApiModel(value = "通过一个产品模板快速复制产品质检模板", description = "通过一个产品模板快速复制产品质检模板")
@Data // 生成getter,setter等函数
@AllArgsConstructor // 生成全参数构造函数
@NoArgsConstructor // 生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class SaveByCopyIdEnter extends GeneralEnter {

    @ApiModelProperty(value = "当前产品Id")
    private Long id;

    @ApiModelProperty(value = "当前产品类型")
    private Integer productionProductType;

    @ApiModelProperty(value = "上一个产品Id")
    private Long lastProductId;

    @ApiModelProperty(value = "上一个产品类型")
    private Integer lastProductionProductType;
}
