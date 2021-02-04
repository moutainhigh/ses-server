package com.redescooter.ses.mobile.rps.vo.restproductionorder.consign;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import lombok.*;

import io.swagger.annotations.*;

/**
 * @ClassName:ConsignDetailProductResult
 * @description: ConsignDetailProductResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/11/03 18:39 
 */
@ApiModel(value = "详情产品列表", description = "详情产品列表")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
public class ConsignDetailProductResult extends GeneralResult {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "产品Id")
    private Long productId;

    @ApiModelProperty(value = "产品编号")
    private String productN;

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
}
