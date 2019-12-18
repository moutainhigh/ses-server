package com.redescooter.ses.web.ros.vo;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;

import io.swagger.annotations.*;

/**
 * @ClassName:DeleteCustomerEnter
 * @description: DeleteCustomerEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/18 17:49
 */
@ApiModel(value = "删除客户入参", description = "删除客户出参")
public class DeleteCustomerEnter extends GeneralEnter {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "删除原因")
    private String reason;
}
