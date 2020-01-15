package com.redescooter.ses.web.delivery.vo.task;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;

/**
 * @ClassName:DriverListResult
 * @description: DriverListResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/01/15 16:59
 */
@ApiModel(value = "司机列表", description = "司机列表")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class DriverListResult extends GeneralResult {
    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "司机姓名")
    private String driverFirstName;

    @ApiModelProperty(value = "司机姓名")
    private String driverLastName;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "已分配订单数")
    private Integer count = 0;
}
