package com.redescooter.ses.web.ros.vo.assign.tobe.enter;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.redescooter.ses.api.common.vo.base.PageEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description 客户列表待分配入参
 * @Author Chris
 * @Date 2020/12/27 18:25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "客户列表待分配入参", description = "客户列表待分配入参")
public class ToBeAssignListEnter extends PageEnter implements Serializable {

    private static final long serialVersionUID = 7101567836984050684L;

    @ApiModelProperty(value = "生成时间开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date generateDateStartTime;

    @ApiModelProperty(value = "生成时间结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date generateDateEndTime;

    @ApiModelProperty(value = "数量范围最小值")
    private Integer scooterQuantityMin;

    @ApiModelProperty(value = "数量范围最大值")
    private Integer scooterQuantityMax;

    @ApiModelProperty(value = "关键字(搜索客户名称或邮箱)")
    private String keyword;

}
