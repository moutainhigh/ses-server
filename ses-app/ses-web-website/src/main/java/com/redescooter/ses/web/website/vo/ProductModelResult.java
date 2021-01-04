package com.redescooter.ses.web.website.vo;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author
 * @Date
 * @Description //TODO
 **/

@Data
public class ProductModelResult extends GeneralResult {
    /**
     * 产品类型单项编码
     */
    @ApiModelProperty(value="产品类型单项编码")
    private String productModelCode;

    /**
     * 产品类型单项名称
     */
    @ApiModelProperty(value="产品类型单项名称")
    private String productModelName;

}
