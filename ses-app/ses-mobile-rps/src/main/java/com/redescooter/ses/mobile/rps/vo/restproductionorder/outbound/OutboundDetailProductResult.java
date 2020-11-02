package com.redescooter.ses.mobile.rps.vo.restproductionorder.outbound;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import lombok.*;

import io.swagger.annotations.*;

/**
 * @ClassName:OutboundDetailProductResult
 * @description: OutboundDetailProductResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/11/02 13:29 
 */
@ApiModel(value = "详情产品列表", description = "详情产品列表")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
public class OutboundDetailProductResult extends GeneralResult {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "产品Id 部件、组装件使用")
    private Long productId;

    @ApiModelProperty(value = "产品类型 部件、组装件使用")
    private Integer productType;

    @ApiModelProperty(value = "产品名称 部件、组装件使用")
    private String productName;

    @ApiModelProperty(value = "产品编号 部件、组装件使用")
    private String productN;

    @ApiModelProperty(value = "颜色Id 车辆使用")
    private Long colorId;

    @ApiModelProperty(value = "颜色名称  车辆使用")
    private String colorName;

    @ApiModelProperty(value = "颜色色值 车辆使用")
    private String colorValue;

    @ApiModelProperty(value = "分组Id 车辆使用")
    private Long groupId;

    @ApiModelProperty(value = "分组名称 车辆使用")
    private String groupName;

    @ApiModelProperty(value = "idclass 表示是否有序列号")
    private Boolean idClass;

    @ApiModelProperty(value = "出库总数量")
    private Integer stockTotal;

    @ApiModelProperty(value = "已出库数量")
    private Integer stockQty;
}
