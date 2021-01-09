package com.redescooter.ses.web.website.vo.order;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author jerry
 * @Date 2021/1/9 7:23 下午
 * @Description 订单修改VO
 **/

@ApiModel(value = "新增订单入参", description = "新增订单入参")
@Data
@EqualsAndHashCode(callSuper = true)
public class ModifyOrderEnter extends GeneralEnter {
}
