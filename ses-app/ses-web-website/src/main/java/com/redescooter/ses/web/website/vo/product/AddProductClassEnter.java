package com.redescooter.ses.web.website.vo.product;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author jerry
 * @Date 2021/1/5 3:53 下午
 * @Description 产品种类新增入参
 **/

@ApiModel(value = "产品种类新增入参", description = "产品种类新增入参")
@Data
@EqualsAndHashCode(callSuper = true)
public class AddProductClassEnter extends GeneralEnter {

    /**
     * 产品种类名称
     */
    @ApiModelProperty(value = "产品种类名称")
    private String productClassName;

    /**
     * 中文名称
     */
    @ApiModelProperty(value = "中文名称")
    private String cnName;

    /**
     * 法文名称
     */
    @ApiModelProperty(value = "法文名称")
    private String frName;

    /**
     * 英文名称
     */
    @ApiModelProperty(value = "英文名称")
    private String enName;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

}
