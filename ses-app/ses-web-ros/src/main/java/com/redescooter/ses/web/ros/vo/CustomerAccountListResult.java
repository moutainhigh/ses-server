package com.redescooter.ses.web.ros.vo;

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

/**
 * @ClassName:CustomerAccountListResult
 * @description: CustomerAccountListResult
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/18 16:46
 */
@ApiModel(value = "账户列表出参", description = "账户列表出参")
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerAccountListResult extends GeneralResult {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "名字")
    private String firstName;

    @ApiModelProperty(value = "名字")
    private String lastName;

    @ApiModelProperty(value = "email")
    private String email;

    @ApiModelProperty(value = "status")
    private String status;

    @ApiModelProperty(value = "客户类型")
    private String userType;

    @ApiModelProperty(value = "行业")
    private String industry;

    @ApiModelProperty(value = "激活日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String activationTime;

    @ApiModelProperty(value = "到期日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String expirationTime;

}
