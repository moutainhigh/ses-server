package com.redescooter.ses.web.ros.vo.codebase;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.redescooter.ses.api.common.constant.DateConstant;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Description
 * @Author Chris
 * @Date 2021/5/14 11:33
 */
@Data
@ApiModel(value = "VIN列表出参")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class VINListResult extends GeneralResult {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "VIN")
    private String vin;

    @ApiModelProperty(value = "状态 1待分配 2已分配")
    private Integer status;

    @JsonFormat(pattern = DateConstant.DEFAULT_DATETIME_FORMAT)
    @ApiModelProperty(value = "生成时间")
    private Date generateDate;

    @JsonFormat(pattern = DateConstant.DEFAULT_DATETIME_FORMAT)
    @ApiModelProperty(value = "更新时间")
    private Date finishDate;

}
