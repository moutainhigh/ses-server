package com.redescooter.ses.api.foundation.vo.common;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * description: CityResult
 * author: jerry.li
 * create: 2019-05-27 16:41
 */


@Data
@ApiModel(value = "城市查询参数", description = "城市查询参数")
public class CityEnter extends GeneralEnter {

    @ApiModelProperty(value = "主键ID")
    private Long id;

    @ApiModelProperty(value = "编码")
    private String code;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "级别")
    private Integer level;

    @ApiModelProperty(value = "父ID")
    private Long pId;

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "语言")
    private String language;

    @ApiModelProperty(value = "经度")
    private BigDecimal longitude;

    @ApiModelProperty(value = "维度")
    private BigDecimal latitude;

}
