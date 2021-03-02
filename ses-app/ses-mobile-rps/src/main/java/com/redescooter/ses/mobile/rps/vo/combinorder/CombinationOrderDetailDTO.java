package com.redescooter.ses.mobile.rps.vo.combinorder;

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
 * 组装单详情对象 DTO
 * @author assert
 * @date 2021/1/27 10:11
 */
@Data
@ApiModel(value = "组装单详情对象")
public class CombinationOrderDetailDTO extends GeneralResult {

    @ApiModelProperty(value = "主键id", dataType = "Long")
    private Long id;

    @ApiModelProperty(value = "组装单号", dataType = "String")
    private String combinNo;

    @ApiModelProperty(value = "组装单状态 1草稿 10待备料 20备料完成(待组装) 30组装中 40组装完成 45质检中 50质检完成",
            dataType = "Integer")
    private Integer status;

    @ApiModelProperty(value = "组装数量", dataType = "Integer")
    private Integer qty;

    @ApiModelProperty(value = "组装单类型 1车辆 2组装件", dataType = "Integer")
    private Integer type;

    @DateTimeFormat(pattern = DateConstant.DEFAULT_DATE_FORMAT_TWO)
    @JsonFormat(pattern = DateConstant.DEFAULT_DATE_FORMAT_TWO, timezone = DateConstant.UTC)
    @ApiModelProperty(value = "组装开始日期")
    private Date combinStartDate;

    @DateTimeFormat(pattern = DateConstant.DEFAULT_DATE_FORMAT_TWO)
    @JsonFormat(pattern = DateConstant.DEFAULT_DATE_FORMAT_TWO, timezone = DateConstant.UTC)
    @ApiModelProperty(value = "组装结束日期")
    private Date combinEndDate;

    @ApiModelProperty(value = "组装清单列表")
    private List<CombinationListDTO> combinationList;

}
