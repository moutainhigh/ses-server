package com.redescooter.ses.web.ros.vo.restproductionorder;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

/**
 *  @author: alex
 *  @Date: 2020/10/26 14:05
 *  @version：V ROS 1.8.3
 *  @Description:
 */
@ApiModel(value = "", description = "")
@Data
@EqualsAndHashCode(callSuper = true)
public class OrderProductDetailResult extends GeneralResult {
    @ApiModelProperty(value = "字单据Id")
    private Long id;

    @ApiModelProperty(value = "分组Id")
    private Long categoryId;

    @ApiModelProperty(value = "分组名称")
    private String categoryName;

    @ApiModelProperty(value = "颜色Id")
    private Long colorId;

    @ApiModelProperty(value = "颜色名称")
    private String colorName;

    @ApiModelProperty(value = "色值")
    private String colorValue;

    @ApiModelProperty(value = "组合、部件名称")
    private String name;

    @ApiModelProperty(value = "序列号")
    private String serialN;

    @ApiModelProperty(value = "部品类型")
    private Integer partType;

    @ApiModelProperty(value = "已发货数量")
    private Integer qty;

    @ApiModelProperty(value = "应发货总数量")
    private Integer totalQty;

    @ApiModelProperty(value = "SN集合")
    private Map<Long, String> snMap;
}
