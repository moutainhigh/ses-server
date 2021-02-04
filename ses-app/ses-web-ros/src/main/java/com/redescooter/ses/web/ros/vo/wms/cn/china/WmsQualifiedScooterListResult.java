package com.redescooter.ses.web.ros.vo.wms.cn.china;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description:
 * @author: Aleks
 * @create: 2020/12/30 11:19
 */
@Data
@ApiModel("不合格品库车辆列表出参")
public class WmsQualifiedScooterListResult extends GeneralResult {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("车型名称")
    private String groupName;

    @ApiModelProperty("颜色名称")
    private String colorName;

    @ApiModelProperty("色值")
    private String colorValue;

    @ApiModelProperty("库存数量")
    private Integer qty;



}
