package com.redescooter.ses.web.ros.vo.wms.cn.fr;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @description:
 * @author: Aleks
 * @create: 2020/12/31 13:42
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("法国仓库今日入库/出库的统计出参")
public class FrTodayInOrOutStockCountResult extends GeneralResult {

    @ApiModelProperty("出入库类型，1：今日入库，2：今日出库")
    private Integer type;

    @ApiModelProperty("车辆数量")
    private Integer scooterNum = 0;

    @ApiModelProperty("组装件")
    private Integer combinNum = 0;

    @ApiModelProperty("部件")
    private Integer partsNum = 0;

    @ApiModelProperty("总数量")
    private Integer countNum = 0;

}
