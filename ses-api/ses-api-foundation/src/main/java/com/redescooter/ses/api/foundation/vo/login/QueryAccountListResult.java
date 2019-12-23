package com.redescooter.ses.api.foundation.vo.login;

import com.redescooter.ses.api.common.vo.base.PageResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName:QueryAccountListResult
 * @description: QueryAccountListResult
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/23 18:12
 */
@ApiModel(value = "账户列表出参", description = "账户列表出参")
@Data
public class QueryAccountListResult extends PageResult {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "customerId")
    private Long customerId;

    @ApiModelProperty(value = "customerFirstName")
    private String customerFirstName;

    @ApiModelProperty(value = "customerLastName")
    private String customerLastName;

    @ApiModelProperty(value = "customerFullName")
    private String customerFullName;

    @ApiModelProperty(value = "email")
    private String email;

    @ApiModelProperty(value = "status")
    private String status;

    @ApiModelProperty(value = "类型")
    private String customerType;

    @ApiModelProperty(value = "行业")
    private String industryType;

    @ApiModelProperty(value = "激活时间")
    private String activationTime;

    @ApiModelProperty(value = "到期时间")
    private String expirationTime;
}
