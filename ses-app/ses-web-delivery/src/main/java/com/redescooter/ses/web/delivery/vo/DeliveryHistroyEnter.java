package com.redescooter.ses.web.delivery.vo;

import com.redescooter.ses.api.common.vo.base.PageEnter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;

/**
 * @ClassName:DeliveryHistroyEnter
 * @description: DeliveryHistroyEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/01/06 17:31
 */
@ApiModel(value = "司机订单历史", description = "司机订单历史")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class DeliveryHistroyEnter extends PageEnter {

    @ApiModelProperty(value = "id")
    private Long id;
}
