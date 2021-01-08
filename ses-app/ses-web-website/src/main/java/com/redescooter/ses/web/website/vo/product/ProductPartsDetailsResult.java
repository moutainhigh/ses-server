package com.redescooter.ses.web.website.vo.product;

import com.baomidou.mybatisplus.annotation.TableField;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author jerry
 * @Date 2021/1/6 3:32 上午
 * @Description 产品配件配置结果集出参
 **/
@ApiModel(value = "产品配件配置结果集出参", description = "产品配件配置结果集出参")
@Data
@EqualsAndHashCode(callSuper = true)
public class ProductPartsDetailsResult extends GeneralResult {
    /**
     * 主键 主键
     */
    @ApiModelProperty(value = "主键 主键")
    private Long id;

    /**
     * 配件ID
     */
    @ApiModelProperty(value = "配件ID")
    private Long partsId;

    /**
     * 产品ID
     */
    @ApiModelProperty(value = "产品ID")
    private Long productId;

    /**
     * 数量
     */
    @ApiModelProperty(value = "数量")
    private Integer qty;

    /**
     * 相关参数
     */
    @TableField(value = "`parameter`")
    @ApiModelProperty(value = "相关参数")
    private String parameter;
}
