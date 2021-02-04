package com.redescooter.ses.web.ros.vo.wms.cn.china;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @description:
 * @author: Aleks
 * @create: 2020/12/28 16:16
 */
@Data
@ApiModel("成品库库存的详情出参")
public class WmsfinishScooterDetailResult extends GeneralResult {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("车型名称")
    private String groupName;

    @ApiModelProperty("颜色名称")
    private String colorName;

    @ApiModelProperty("色值")
    private String colorValue;

    @ApiModelProperty("入库记录")
    private List<WmsStockRecordResult> recordList;

}
