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

import java.util.List;

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

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "customerType")
    private String customerType;

    @ApiModelProperty(value = "industryType")
    private String industryType;

    @ApiModelProperty(value = "status")
    private String status;

    @ApiModelProperty(value = "customerLastName")
    private String customerLastName;

    @ApiModelProperty(value = "customerFirstName")
    private String customerFirstName;

    @ApiModelProperty(value = "customerFullName")
    private String customerFullName;

    @ApiModelProperty(value = "email")
    private String email;

    @ApiModelProperty(value = "激活时间 为了和 开通账户入参保持一致")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="UTC")
    private String startActivationTime;

    @ApiModelProperty(value = "到期时间 为了和 开通账户入参保持一致")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="UTC")
    private String endActivationTime;

    @ApiModelProperty(value = "账户节点")
    private List<AccountNodeResult> accountNodeList;
}
