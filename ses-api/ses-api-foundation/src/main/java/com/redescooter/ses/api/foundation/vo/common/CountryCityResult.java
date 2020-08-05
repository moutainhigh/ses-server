package com.redescooter.ses.api.foundation.vo.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassNameCountryCityResult
 * @Description
 * @Author Aleks
 * @Date2020/7/27 19:56
 * @Version V1.0
 **/
@Data
public class CountryCityResult implements Serializable {

    @ApiModelProperty("主键id")
    private Long id;

//    @ApiModelProperty("编码")
//    private String code;

    @ApiModelProperty("名称")
    private String name;

//    @ApiModelProperty("等级")
//    private Integer level;

//    @ApiModelProperty("国家包含的城市")
//    private List<CityNameResult> children;

}
