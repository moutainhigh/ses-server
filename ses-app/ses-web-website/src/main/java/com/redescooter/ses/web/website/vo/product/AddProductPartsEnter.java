package com.redescooter.ses.web.website.vo.product;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author jerry
 * @Date 2021/1/6 3:31 上午
 * @Description 新增产品配件配置入参
 **/
@ApiModel(value = "新增产品配件配置入参", description = "新增产品配件配置入参")
@Data
@EqualsAndHashCode(callSuper = true)
public class AddProductPartsEnter extends GeneralEnter {

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

    /**
     * 备注
     */
    @TableField(value = "remark")
    @ApiModelProperty(value = "备注")
    private String remark;

}
