package com.redescooter.ses.api.foundation.vo.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassNameCityNameResult
 * @Description
 * @Author Aleks
 * @Date2020/7/28 10:55
 * @Version V1.0
 **/
@Data
public class CityNameResult implements Serializable {

    @ApiModelProperty("城市名称")
    private String cityName;

    @ApiModelProperty("城市下面的邮编")
    private List<String> children;

}
