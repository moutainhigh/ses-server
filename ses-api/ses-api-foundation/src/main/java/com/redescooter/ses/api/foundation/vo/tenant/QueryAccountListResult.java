package com.redescooter.ses.api.foundation.vo.tenant;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @ClassName:QueryAccountListResult
 * @description: QueryAccountListResult
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/23 18:12
 */
@ApiModel(value = "账户列表出参", description = "账户列表出参")
@Data
public class QueryAccountListResult extends GeneralResult {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "inputTenantId")
    private Long inputTenantId;

    @ApiModelProperty(value = "status")
    private String status;

    @ApiModelProperty(value = "激活时间")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="UTC")
    private Date activationTime;

    @ApiModelProperty(value = "到期时间")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="UTC")
    private Date expirationTime;
}
