package com.redescooter.ses.web.ros.vo.wms.cn.china;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description:
 * @author: Aleks
 * @create: 2020/12/30 14:29
 */
@Data
@ApiModel("不合格品库库存统计出参")
public class WmsQualifiedQtyCountResult extends GeneralResult {

    @ApiModelProperty("不合格库存")
    private Integer qty;

}
