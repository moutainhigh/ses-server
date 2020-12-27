package com.redescooter.ses.web.ros.vo.wms.cn.china;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @ClassName:stockCountResult
 * @description: stockCountResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/12/27 15:48
 */
@Data
@ApiModel(value = "库存统计出参对象")
public class StockCountResult extends GeneralResult {

    @ApiModelProperty("库存类型，1：成品库，2：原料库，3：不合格品库")
    private Integer type;

    @ApiModelProperty("车辆数量")
    private Integer scooterNum = 0;

    @ApiModelProperty("组装件数量")
    private Integer combingNum = 0;

    @ApiModelProperty("部件数量")
    private Integer partsNum = 0;

    @ApiModelProperty("总数量")
    private Integer countNum = 0;

}
