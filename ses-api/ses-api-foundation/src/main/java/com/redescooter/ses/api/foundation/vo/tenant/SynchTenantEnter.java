package com.redescooter.ses.api.foundation.vo.tenant;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassNameSynchTenant
 * @Description
 * @Author Aleks
 * @Date2020/8/26 17:06
 * @Version V1.0
 **/
@Data
public class SynchTenantEnter extends GeneralEnter {

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "企业名称")
    private String companyName;

    @ApiModelProperty(value = "地址")
    private String address;
}