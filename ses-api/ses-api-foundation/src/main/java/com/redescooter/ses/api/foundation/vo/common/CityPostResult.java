package com.redescooter.ses.api.foundation.vo.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassNameCityPostResult
 * @Description
 * @Author Aleks
 * @Date2020/7/27 18:54
 * @Version V1.0
 **/
@Data
public class CityPostResult {

//    @ApiModelProperty("主键id")
//    private Long id;

    @ApiModelProperty("邮政编码")
    private String postCode;

}
