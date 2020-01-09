package com.redescooter.ses.web.delivery.vo;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;

/**
 * @ClassName:UpdateCustomerInfoEnter
 * @description: UpdateCustomerInfoEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/01/09 16:38
 */
@ApiModel(value = "更新客户信息", description = "更新客户信息")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class UpdateCustomerInfoEnter extends GeneralEnter {

    @ApiModelProperty(value = "姓名")
    private String firstName;

    @ApiModelProperty(value = "姓名")
    private String lastName;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "头像")
    private String avatar;
}
