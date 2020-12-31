package com.redescooter.ses.api.common.vo.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 下拉框查询统一返回结果 DTO
 * @author assert
 * @date 2020/12/9 16:40
 */
@Data
@ApiModel(value = "下拉框查询统一返回结果")
public class SelectBaseResultDTO implements Serializable {

    @ApiModelProperty(value = "主键id", dataType = "String")
    private Long id;

    @ApiModelProperty(value = "名称", dataType = "String")
    private String name;

}
