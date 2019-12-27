package com.redescooter.ses.api.common.vo.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 27/12/2019 4:11 下午
 * @ClassName: BaseCountryCodeResult
 * @Function: TODO
 */
@ApiModel(value = "国家编码入参", description = "国家编码入参")
@Data // 生成getter,setter等函数
@AllArgsConstructor // 生成全参数构造函数
@NoArgsConstructor // 生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class BaseCountryCodeEnter extends GeneralEnter {
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Long id;

    /**
     * 国家名称
     */
    @ApiModelProperty(value = "国家名称")
    private String countryName;

    /**
     * 国家语言
     */
    @ApiModelProperty(value = "国家语言")
    private String countryLanguage;

}
