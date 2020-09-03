package com.redescooter.ses.api.common.vo.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassNameCityNameEnter
 * @Description
 * @Author Aleks
 * @Date2020/7/28 10:28
 * @Version V1.0
 **/
@Data
public class CityNameEnter extends GeneralEnter{

    @ApiModelProperty("查询等级,1:查询国家,2:查询城市,3:查询邮编")
    private Integer level;


    @ApiModelProperty("国家id，查询城市的时候必须要传这个")
    private Long id;

    @ApiModelProperty("城市,查询邮编的时候必传")
    private String city;

    @ApiModelProperty("城市名称")
    private String keyWord;

}
