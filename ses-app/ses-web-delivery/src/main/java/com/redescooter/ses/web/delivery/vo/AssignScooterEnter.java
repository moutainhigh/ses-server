package com.redescooter.ses.web.delivery.vo;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 2/1/2020 6:52 下午
 * @ClassName: AssignScooterEnter
 * @Function: TODO
 */
@ApiModel(value = "车辆分配入参", description = "车辆分配入参")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class AssignScooterEnter extends GeneralEnter {

    @ApiModelProperty(value = "司机id")
    private long driverId;
    @ApiModelProperty(value = "车辆id")
    private long scooterId;
}


