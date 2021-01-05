package com.redescooter.ses.mobile.rps.vo.outwhorder;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 出库单详情结果对象 DTO
 * @author assert
 * @date 2021/1/4 16:52
 */
@Data
@ApiModel(value = "出库单详情结果对象")
public class OutWarehouseOrderDetailDTO extends GeneralResult {

    @ApiModelProperty(value = "出库单id", dataType = "Long")
    private Long id;

    @ApiModelProperty(value = "出库单号", dataType = "String")
    private String outWarehouseNo;

    @ApiModelProperty(value = "出库单状态 0：待出库，10：质检中，15：部分出库，20：已出库，30：已取消", dataType = "Integer")
    private Integer status;

    @ApiModelProperty(value = "出库单类型 1整车 2组装件 3部件", dataType = "Integer")
    private Integer orderType;

    @ApiModelProperty(value = "出库总数", dataType = "Integer")
    private Integer qty;

    @ApiModelProperty(value = "质检数量", dataType = "Integer")
    private Integer qcQty;

    @ApiModelProperty(value = "备注", dataType = "String")
    private String remark;

    @ApiModelProperty(value = "出库单产品信息")
    private List<OutWarehouseOrderProductDTO> productList;

}
