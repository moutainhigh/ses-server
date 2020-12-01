package com.redescooter.ses.web.ros.vo.restproductionorder.allocateorder;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassNameWhDataResult
 * @Description
 * @Author Aleks
 * @Date2020/10/26 18:33
 * @Version V1.0
 **/
@Data
@ApiModel(value = "仓库下拉数据出参",description = "仓库下拉数据出参")
public class WhDataResult extends GeneralResult {

    @ApiModelProperty("仓库id")
    private Long whId;

    @ApiModelProperty("仓库名称")
    private String whName;

}
