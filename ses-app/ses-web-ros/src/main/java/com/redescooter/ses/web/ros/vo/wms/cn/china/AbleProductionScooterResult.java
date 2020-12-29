package com.redescooter.ses.web.ros.vo.wms.cn.china;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description:
 * @author: Aleks
 * @create: 2020/12/29 14:02
 */
@Data
@ApiModel("可生成的车型颜色及数量")
public class AbleProductionScooterResult extends GeneralResult {

    @ApiModelProperty("车型名称")
    private String groupName;

    @ApiModelProperty("颜色名称")
    private String colorName;

    @ApiModelProperty("数量")
    private Integer num;

}
