package com.redescooter.ses.web.ros.vo.codebase;

import com.redescooter.ses.api.common.constant.DateConstant;
import com.redescooter.ses.api.common.vo.base.PageEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Description
 * @Author Chris
 * @Date 2021/5/14 11:27
 */
@Data
@ApiModel(value = "RSN列表入参")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class RSNListEnter extends PageEnter {

    @ApiModelProperty(value = "RSN")
    private String rsn;

    @ApiModelProperty(value = "状态 0全部 1待分配 2已分配")
    private Integer status;

    @DateTimeFormat(pattern = DateConstant.DEFAULT_DATETIME_FORMAT)
    @ApiModelProperty(value = "生成时间开始")
    private Date generateDateStartTime;

    @DateTimeFormat(pattern = DateConstant.DEFAULT_DATETIME_FORMAT)
    @ApiModelProperty(value = "生成时间结束")
    private Date generateDateEndTime;

    @DateTimeFormat(pattern = DateConstant.DEFAULT_DATETIME_FORMAT)
    @ApiModelProperty(value = "更新时间开始")
    private Date finishDateStartTime;

    @DateTimeFormat(pattern = DateConstant.DEFAULT_DATETIME_FORMAT)
    @ApiModelProperty(value = "更新时间结束")
    private Date finishDateEndTime;

}
