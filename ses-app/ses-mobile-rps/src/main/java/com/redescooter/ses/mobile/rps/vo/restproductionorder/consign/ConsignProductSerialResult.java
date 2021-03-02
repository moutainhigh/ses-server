package com.redescooter.ses.mobile.rps.vo.restproductionorder.consign;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import lombok.*;

import java.util.List;

import io.swagger.annotations.*;

/**
 * @ClassName:ConsignProductSerialResult
 * @description: ConsignProductSerialResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/11/05 13:55 
 */
@ApiModel(value = "", description = "")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
public class ConsignProductSerialResult extends GeneralResult {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "子订单Id")
    private Long orderBId;

    @ApiModelProperty(value = "产品Id")
    private Long productId;

    @ApiModelProperty(value = "产品编号")
    private String productN;

    @ApiModelProperty(value = "产品类型")
    private Integer productType;

    @ApiModelProperty(value = "产品名称")
    private String productName;

    @ApiModelProperty(value = "是否有序列号")
    private Boolean idclass;

    @ApiModelProperty(value = "分组Id")
    private Long groupId;

    @ApiModelProperty(value = "分组")
    private String groupName;

    @ApiModelProperty(value = "颜色Id")
    private Long colorId;

    @ApiModelProperty(value = "颜色名字")
    private String colorName;

    @ApiModelProperty(value = "颜色色值")
    private String colorValue;

    @ApiModelProperty(value = "产品序列号")
    private Long serialN;

    @ApiModelProperty(value = "应发货数量")
    private Integer qty;
}
