package com.redescooter.ses.web.ros.vo.assign.tobe.result;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
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
 * @Description 客户列表待分配出参
 * @Author Chris
 * @Date 2020/12/27 18:15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "客户列表待分配出参", description = "客户列表待分配出参")
public class ToBeAssignListResult extends GeneralResult implements Serializable {

    private static final long serialVersionUID = 7213624055862883937L;

    @ApiModelProperty(value = "客户id")
    private Long customerId;

    @ApiModelProperty(value = "客户名称")
    private String fullName;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "数量")
    private Integer scooterQuantity;

    @ApiModelProperty(value = "生成时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date generateDate;

}
