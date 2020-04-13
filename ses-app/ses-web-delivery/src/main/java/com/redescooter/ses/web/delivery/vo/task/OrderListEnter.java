package com.redescooter.ses.web.delivery.vo.task;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.PageEnter;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;

import java.util.List;

/**
 * @ClassName:OrderListEnter
 * @description: OrderListEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/01/16 18:27
 */
@ApiModel(value = "", description = "")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class OrderListEnter extends PageEnter {

    @ApiModelProperty(value = "过滤已选择订单")
    private List<Long> filterIds;

    @ApiModelProperty(value = "收货方城市")
    private String recipientCity;

    @ApiModelProperty(value = "收货方邮编")
    private String recipientPostcode;

    @ApiModelProperty(value = "创建开始时间")
    private String createdStartTime;

    @ApiModelProperty(value = "创建结束时间")
    private String createdEndTime;

    @ApiModelProperty(value = "期望送达开始时间")
    private String expectTimeStart;

    @ApiModelProperty(value = "期望送达结束时间")
    private String expectTimeEnd;
}
