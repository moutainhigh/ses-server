package com.redescooter.ses.web.ros.vo.website;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;

/**
 * @ClassName:OrderFormsEnter
 * @description: OrderFormsEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/05/13 19:24
 */
@ApiModel(value = "", description = "")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class OrderFormsEnter extends GeneralEnter {

    @ApiModelProperty(value = "支付状态")
    private String type;
}
