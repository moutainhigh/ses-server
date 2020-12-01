package com.redescooter.ses.api.common.vo.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @ClassNameCityNameEnter
 * @Description
 * @Author Aleks
 * @Date2020/7/28 10:28
 * @Version V1.0
 **/
@ApiModel(value = "City Enter", description = "City Enter")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class CityNameEnter extends GeneralEnter {

    @ApiModelProperty(value = "Query level", notes = "1: country, 2: City, 3: postcode")
    private Integer level;

    @ApiModelProperty(value = "Country id", notes = "this must be passed when querying a city")
    private Long id;

    @ApiModelProperty(value = "City", notes = "it must be sent when you query the postcode")
    private String city;

    @ApiModelProperty(value = "keyWord")
    private String keyWord;

}
