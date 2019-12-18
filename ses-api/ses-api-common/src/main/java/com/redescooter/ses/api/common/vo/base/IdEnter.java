package com.redescooter.ses.api.common.vo.base;

import lombok.Data;

import io.swagger.annotations.*;

/**
 * @ClassName:IdEnter
 * @description: IdEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/18 16:32
 */
@ApiModel(value = "Id入参", description = "Id入参")
@Data
public class IdEnter extends GeneralEnter {

    @ApiModelProperty(value = "业务Id")
    private Long id;
}
