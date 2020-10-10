package com.redescooter.ses.api.foundation.vo.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassNameCountryCityResult
 * @Description
 * @Author Aleks
 * @Date2020/7/27 19:56
 * @Version V1.0
 **/
@ApiModel(value = "Country City Result", description = "Country City Result")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class CountryCityResult implements Serializable {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("name")
    private String name;
}
