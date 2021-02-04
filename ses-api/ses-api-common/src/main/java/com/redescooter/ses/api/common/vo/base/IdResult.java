package com.redescooter.ses.api.common.vo.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @ClassName:IdEnter
 * @description: IdResult
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/18 16:32
 */
@ApiModel(value = "Primary key parameters", description = "Primary key parameters")
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class IdResult extends GeneralResult {

    @ApiModelProperty(value = "ID", required = true)
    private Long id;
}
