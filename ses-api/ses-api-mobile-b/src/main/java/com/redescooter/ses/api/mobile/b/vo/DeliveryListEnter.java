package com.redescooter.ses.api.mobile.b.vo;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName:DeliveryListEnter
 * @description: DeliveryListEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/01/03 15:06
 */
@ApiModel(value = "订单列表入参", description = "订单列表入参")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
public class DeliveryListEnter extends GeneralEnter {

    @ApiModelProperty(value = "状态列表")
    private List<String> statusList;
}
