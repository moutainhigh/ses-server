package com.redescooter.ses.web.ros.vo.customer;

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

import java.util.Date;

/**
 * @ClassName:AccountListResult
 * @description: AccountListResult
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/23 13:09
 */
@ApiModel(value = "账户列表出参", description = "账户列表出参")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class AccountListResult extends GeneralResult {

    @ApiModelProperty(value = "customerId")
    private Long id;

    @ApiModelProperty(value = "tenantId")
    private Long tenantId;

    @ApiModelProperty(value = "customerFirstName")
    private String customerFirstName;

    @ApiModelProperty(value = "customerLastName")
    private String customerLastName;

    @ApiModelProperty(value = "customerFullName")
    private String customerFullName;

    @ApiModelProperty(value = "联系人名字")
    private String contactFirstName;

    @ApiModelProperty(value = "联系人姓氏")
    private String contactLastName;

    @ApiModelProperty(value = "联系人全名")
    private String contactFullName;

    @ApiModelProperty(value = "公司名称")
    private String companyName;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "类型")
    private String customerType;

    @ApiModelProperty(value = "行业")
    private String industryType;

    @ApiModelProperty(value = "激活时间")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="UTC")
    private Date activationTime;

    @ApiModelProperty(value = "到期时间")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="UTC")
    private Date expirationTime;
}
