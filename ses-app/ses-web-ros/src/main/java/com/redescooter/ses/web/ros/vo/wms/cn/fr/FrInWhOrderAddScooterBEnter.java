package com.redescooter.ses.web.ros.vo.wms.cn.fr;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Description 新增法国仓库入库单产品明细入参
 * @Author Chris
 * @Date 2021/4/15 10:56
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ApiModel(value = "新增法国仓库入库单产品明细入参", description = "新增法国仓库入库单产品明细入参")
public class FrInWhOrderAddScooterBEnter extends GeneralEnter {

    @ApiModelProperty(value = "车型id", required = true)
    private Long groupId;

    @ApiModelProperty(value = "颜色id", required = true)
    private Long colorId;

    @ApiModelProperty(value = "平板序列号", required = true)
    private String sn;

    @ApiModelProperty(value = "蓝牙地址", required = true)
    private String bluetoothMacAddress;

}
