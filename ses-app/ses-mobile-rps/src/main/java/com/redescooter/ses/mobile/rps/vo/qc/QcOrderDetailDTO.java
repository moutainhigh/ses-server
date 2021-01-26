package com.redescooter.ses.mobile.rps.vo.qc;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 质检单详情返回对象 DTO
 * @author assert
 * @date 2021/1/25 17:52
 */
@Data
@ApiModel(value = "质检单详情返回对象")
public class QcOrderDetailDTO extends GeneralResult {

    @ApiModelProperty(value = "主键id", dataType = "Long")
    private Long id;

    @ApiModelProperty(value = "质检单号", dataType = "String")
    private String qcNo;

    @ApiModelProperty(value = "质检数量", dataType = "Integer")
    private Integer qcQty;

    @ApiModelProperty(value = "质检状态 1待质检 10质检中 20质检完成")
    private Integer status;

    @ApiModelProperty(value = "质检单据类型 1车辆 2组装件 3部件")
    private Integer orderType;

    @ApiModelProperty(value = "质检类型 1采购 2退料 3生产 4发货 5返修 6其他")
    private Integer type;

    @ApiModelProperty(value = "质检完成时间")
    private Date qcCompletionTime;

    @ApiModelProperty(value = "待质检产品列表")
    private List<QcOrderProductDTO> pendingQcProductList;

    @ApiModelProperty(value = "质检成功产品列表")
    private List<QcOrderProductDTO> qcSuccessProductList;

    @ApiModelProperty(value = "质检失败产品列表")
    private List<QcOrderProductDTO> qcFailedProductList;

}
