package com.redescooter.ses.web.ros.vo.wms.cn.china;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description:
 * @author: Aleks
 * @create: 2020/12/29 11:58
 */
@Data
@ApiModel("仓库库存的情况统计入参")
public class WmsStockCountEnter extends GeneralEnter {

    @ApiModelProperty("仓库类型，1：中国仓库，2：法国仓库")
    private Integer stockType;

    @ApiModelProperty("产品类型，1：车辆，2：组装件，3：部件")
    private Integer productType;


}
