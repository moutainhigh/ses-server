package com.redescooter.ses.web.ros.vo.customer;

import com.redescooter.ses.api.common.annotation.MaximumLength;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;

import com.redescooter.ses.web.ros.exception.ValidationExceptionCode;
import io.swagger.annotations.*;
import lombok.*;

/**
 * @ClassName:DeleteCustomerEnter
 * @description: TrashCustomerEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/18 17:49
 */
@ApiModel(value = "客户删除", description = "客户删除")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class TrashCustomerEnter extends GeneralEnter {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "删除原因")
    @MaximumLength(value="200",code = ValidationExceptionCode.CHARACTER_IS_TOO_LONG,message = "字符过长")
    private String reason;
}
