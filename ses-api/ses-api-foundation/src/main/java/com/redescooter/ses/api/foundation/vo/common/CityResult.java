package com.redescooter.ses.api.foundation.vo.common;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
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
@Builder
@ApiModel(value = "城市结果集", description = "城市结果集")
public class CityResult extends GeneralResult {

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键ID")
    private Long id;
    /**
     * 编码
     */
    @ApiModelProperty(value = "编码")
    private String code;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    private String name;

    /**
     * 级别，国家为首级，默认为1
     */
    @ApiModelProperty(value = "级别")
    private Integer level;

    /**
     * 父ID，默认首级节点为0
     */
    @ApiModelProperty(value = "父ID")
    private Long pId;

    /**
     * 时区
     */
    @ApiModelProperty(value = "时区")
    private String timeZone;

    /**
     * 语言
     */
    @ApiModelProperty(value = "语言")
    private String language;

    /**
     * 经度
     */
    @ApiModelProperty(value = "经度")
    private BigDecimal longitude;

    /**
     * 纬度
     */
    @ApiModelProperty(value = "纬度")
    private BigDecimal latitude;
}
