package com.redescooter.ses.api.foundation.vo.login;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 25/11/2019 7:12 上午
 * @ClassName: SelectAccountResult
 * @Function: TODO
 */
@ApiModel(value = "账户选择列表", description = "账户选择列表")
@Data
@EqualsAndHashCode(callSuper = false)
public class SelectAccountResult extends GeneralResult {

    @ApiModelProperty(value = "userId")
    private Long userId;
    @ApiModelProperty(value = "tenantId")
    private Long tenantId;
    @ApiModelProperty(value = "systemId")
    private String systemId;
    @ApiModelProperty(value = "appId")
    private String appId;
    @ApiModelProperty(value = "loginName")
    private String loginName;
    @ApiModelProperty(value = "userType")
    private Integer userType;
    @ApiModelProperty(value = "lastLoginToken")
    private String lastLoginToken;
    @ApiModelProperty(value = "头像")
    private String picture;

}
