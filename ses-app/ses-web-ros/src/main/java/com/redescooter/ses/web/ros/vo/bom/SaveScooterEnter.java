package com.redescooter.ses.web.ros.vo.bom;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @ClassName:SaveScooterEnter
 * @description: SaveScooterEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/02/25 10:33
 */
@ApiModel(value = "Bom车辆保存入参", description = "Bom车辆保存入参")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class SaveScooterEnter extends GeneralEnter {

    @ApiModelProperty(value = "产品编号")
    private String productN;

    @ApiModelProperty(value = "产品名字")
    private String productName;

    @ApiModelProperty(value = "生产周期")
    private int procurementCycle;

}
