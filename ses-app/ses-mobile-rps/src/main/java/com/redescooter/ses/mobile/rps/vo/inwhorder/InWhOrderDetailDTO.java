package com.redescooter.ses.mobile.rps.vo.inwhorder;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.redescooter.ses.api.common.constant.DateConstant;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * 入库单详情返回结果对象 DTO
 * @author assert
 * @date 2021/1/15 18:18
 */
@Data
@ApiModel(value = "入库单详情返回结果对象")
public class InWhOrderDetailDTO extends GeneralResult {

    @ApiModelProperty(value = "主键id", dataType = "Long")
    private Long id;

    @ApiModelProperty(value = "入库单号", dataType = "String")
    private String inWhNo;

    @ApiModelProperty(value = "入库数量", dataType = "Integer")
    private Integer qty;

    @ApiModelProperty(value = "入库单类型 1车辆 2组装件 3部件", dataType = "Integer")
    private Integer orderType;

    @ApiModelProperty(value = "入库类型 1：生产入库，2：返修入库，3：采购入库，4：退料入库，5：其他，6:报废入库，7:调拨入库",
            dataType = "Integer")
    private Integer type;

    @ApiModelProperty(value = "入库单状态 入库单状态 1草稿 10已入库", dataType = "Integer")
    private Integer status;

    @ApiModelProperty(value = "备注说明", dataType = "String")
    private String remark;

    @DateTimeFormat(pattern = DateConstant.DEFAULT_DATETIME_FORMAT)
    @JsonFormat(pattern = DateConstant.DEFAULT_DATETIME_FORMAT, timezone = DateConstant.UTC)
    @ApiModelProperty(value = "质检完成时间")
    private Date qcCompletionTime;

    @ApiModelProperty(value = "入库单产品列表")
    private List<InWhOrderProductDTO> productList;

}
