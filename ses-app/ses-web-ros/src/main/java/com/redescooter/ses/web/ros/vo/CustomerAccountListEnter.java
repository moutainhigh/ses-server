package com.redescooter.ses.web.ros.vo;

import com.redescooter.ses.api.common.vo.base.PageEnter;
import lombok.Data;

import io.swagger.annotations.*;

/**
 * @ClassName:CustomerAccountListEnter
 * @description: CustomerAccountListEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/18 17:54
 */
@ApiModel(value = "账户列表入参", description = "账户列表入参")
@Data
public class CustomerAccountListEnter extends PageEnter {

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "客户类型,企业、个人",allowableValues = "ENTERPRISE,PERSONAL")
    private String userType;

    @ApiModelProperty(value = "行业类型, 餐厅、快递",allowableValues = "RESTAURANT,EXPRESS_DELIVERY")
    private String industry;

    @ApiModelProperty(value = "激活开始日期")
    private String startActivationTime;

    @ApiModelProperty(value = "激活结束日期")
    private String endActivationTime;

    @ApiModelProperty(value = "冻结开始日期")
    private String startExpirationTime;

    @ApiModelProperty(value = "冻结结束日期")
    private String endExpirationTime;

    @ApiModelProperty(value = "关键日搜索")
    private String keyword;
}
