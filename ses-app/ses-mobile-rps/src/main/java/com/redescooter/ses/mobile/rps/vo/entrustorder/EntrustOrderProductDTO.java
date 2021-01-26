package com.redescooter.ses.mobile.rps.vo.entrustorder;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 委托单产品信息 DTO
 * @author assert
 * @date 2021/1/4 11:11
 */
@Data
@ApiModel(value = "委托单产品信息")
public class EntrustOrderProductDTO extends GeneralResult {

    @ApiModelProperty(value = "主键id(委托单子单据id)", dataType = "Long")
    private Long id;

    @ApiModelProperty(value = "名称", dataType = "String")
    private String name;

    @ApiModelProperty(value = "产品编号", dataType = "String")
    private String number;

    @ApiModelProperty(value = "数量", dataType = "Integer")
    private Integer qty;

    @ApiModelProperty(value = "实际发货数量", dataType = "Integer")
    private Integer consignorQty;

    @ApiModelProperty(value = "车型(高速、低速) - 车辆委托单使用", dataType = "String")
    private String groupName;

    @ApiModelProperty(value = "颜色名称 - 车辆委托单使用", dataType = "String")
    private String colorName;

    @ApiModelProperty(value = "是否有序列号 true/false", dataType = "boolean")
    private Boolean idClass;

    @ApiModelProperty(value = "产品序列号信息集合对象")
    private List<EntrustProductSerialNumDTO> productSerialNumList;

}
