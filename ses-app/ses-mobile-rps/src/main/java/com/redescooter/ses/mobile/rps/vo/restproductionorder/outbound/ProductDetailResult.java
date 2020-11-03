package com.redescooter.ses.mobile.rps.vo.restproductionorder.outbound;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import lombok.*;

import java.util.List;

import io.swagger.annotations.*;

/**
 * @ClassName:ProductDetailResult
 * @description: ProductDetailResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/11/02 13:39 
 */
@ApiModel(value = "产品", description = "")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
public class ProductDetailResult extends GeneralResult {
    @ApiModelProperty(value = "Id")
    private Long id;

    @ApiModelProperty(value = "产品Id 部件、组合使用")
    private Long productId;

    @ApiModelProperty(value = "产品名称 部件、组合使用")
    private String productName;

    @ApiModelProperty(value = "产品编码 部件、组合使用")
    private String productN;

    @ApiModelProperty(value = "产品颜色Id 车辆使用")
    private Integer colorId;

    @ApiModelProperty(value = "产品颜色名字 车辆使用")
    private String colorName;

    @ApiModelProperty(value = "产品色值 车辆使用")
    private String colorValue;

    @ApiModelProperty(value = "产品分组Id 车辆使用")
    private Integer groupId;

    @ApiModelProperty(value = "分组名称 车辆使用")
    private String groupName;

    @ApiModelProperty(value = "产品类型")
    private Integer productType;

    @ApiModelProperty(value = "出库总数")
    private Integer stockTotal;

    @ApiModelProperty(value = "已经质检出库数量")
    private Integer qcQty;

    @ApiModelProperty(value = "产品sn列表")
    private List<ProductSnResult> productSnResultList;
}
