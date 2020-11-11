package com.redescooter.ses.web.ros.vo.restproductionorder.inwhouse;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassNameInWhRelationOrderResult
 * @Description
 * @Author Aleks
 * @Date2020/11/11 15:46
 * @Version V1.0
 **/
@Data
@ApiModel(value = "入库单关联的单据号",description = "入库单关联的单据号")
public class InWhRelationOrderResult {

    @ApiModelProperty("关联的单据号id")
    private Long relationOrderId;

    @ApiModelProperty("关联的单据号")
    private String relationOrderNo;

}
