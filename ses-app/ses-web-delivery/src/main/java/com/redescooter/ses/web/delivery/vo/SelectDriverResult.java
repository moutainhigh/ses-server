package com.redescooter.ses.web.delivery.vo;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 3/1/2020 5:00 下午
 * @ClassName: ListDeliveryPage
 * @Function: TODO
 */
@ApiModel(value = "司机选择列表", description = "配送单列表")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class SelectDriverResult extends GeneralResult {

    @ApiModelProperty(value = "司机主键")
    private Long driverId;
    @ApiModelProperty(value = "司机用户主键")
    private Long userId;
    @ApiModelProperty(value = "司机名字")
    private String lastName;
    @ApiModelProperty(value = "司机姓氏")
    private String firstName;
    @ApiModelProperty(value = "订单完成数")
    private int completeAmount = 0;

}
