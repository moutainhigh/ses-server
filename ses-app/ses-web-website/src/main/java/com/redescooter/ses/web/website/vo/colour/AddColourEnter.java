package com.redescooter.ses.web.website.vo.colour;

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
@ApiModel(value = "Product color added", description = "产品颜色新增入参")
@Data
@EqualsAndHashCode(callSuper = true)
public class AddColourEnter extends GeneralEnter {

    /**
     * 颜色名称
     */
    @ApiModelProperty(value="Color name")
    private String colourName;

    /**
     * 颜色RGB值
     */
    @ApiModelProperty(value="Color RGB value")
    private String colourRgb;

    /**
     * 颜色16进制颜色编码
     */
    @ApiModelProperty(value="Color hexadecimal color coding")
    private String colour16;

    /**
     * 备注
     */
    @ApiModelProperty(value="remark")
    private String remark;

}
