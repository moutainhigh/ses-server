package com.redescooter.ses.web.ros.vo.account;

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
 * @ClassName:AccountDeatilResult
 * @description: AccountDeatilResult
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/24 16:27
 */
@ApiModel(value = "账户详情出参", description = "账户详情出参")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class AccountDeatilResult extends GeneralResult {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "客户类型")
    private String customerType;

    @ApiModelProperty(value = "行业类型")
    private String industryType;

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "客户名字")
    private String customerLastName;

    @ApiModelProperty(value = "客户姓氏")
    private String customerFirstName;

    @ApiModelProperty(value = "客户全称")
    private String customerFullName;

    @ApiModelProperty(value = "联系人姓氏")
    private String contactFirstName;

    @ApiModelProperty(value = "联系人名字")
    private String contactLastName;

    @ApiModelProperty(value = "联系人全名")
    private String contactFullName;

    @ApiModelProperty(value = "公司名称")
    private String companyName;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "激活时间 为了和 开通账户入参保持一致")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Date activationTime;

    @ApiModelProperty(value = "到期时间 为了和 开通账户入参保持一致")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Date expireTime;
}
