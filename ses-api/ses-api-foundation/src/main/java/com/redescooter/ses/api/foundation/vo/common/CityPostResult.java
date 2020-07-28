package com.redescooter.ses.api.foundation.vo.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassNameCityPostResult
 * @Description
 * @Author Aleks
 * @Date2020/7/27 18:54
 * @Version V1.0
 **/
@Data
public class CityPostResult implements Serializable {

    @ApiModelProperty("邮政编码")
    private String postCode;

}
