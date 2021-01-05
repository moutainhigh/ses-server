package com.redescooter.ses.web.website.vo.product;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author jerry
 * @Date 2021/1/5 10:13 下午
 * @Description 产品颜色新增入参
 **/
@ApiModel(value = "产品颜色新增入参", description = "产品颜色新增入参")
@Data
@EqualsAndHashCode(callSuper = true)
public class AddColourEnter extends GeneralEnter {

    /**
     * 颜色名称
     */
    @ApiModelProperty(value="颜色名称")
    private String colourName;

    /**
     * 颜色编码
     */
    @ApiModelProperty(value="颜色编码")
    private String colourCode;

    /**
     * 颜色RGB值
     */
    @ApiModelProperty(value="颜色RGB值")
    private String colourRgb;

    /**
     * 颜色16进制颜色编码
     */
    @ApiModelProperty(value="颜色16进制颜色编码")
    private String colour16;

    /**
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String remark;

}
