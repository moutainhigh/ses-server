package com.redescooter.ses.web.ros.vo.codebase;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Description
 * @Author Chris
 * @Date 2021/5/14 13:26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class SpecificatResult extends GeneralResult {

    @ApiModelProperty(value = "车型id")
    private Long specificatTypeId;

    @ApiModelProperty(value = "车型名称")
    private String specificatTypeName;

}
