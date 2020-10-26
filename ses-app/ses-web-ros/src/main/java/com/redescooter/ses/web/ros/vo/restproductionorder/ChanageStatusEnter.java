package com.redescooter.ses.web.ros.vo.restproductionorder;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import lombok.*;

import io.swagger.annotations.*;

/**
 *  @author: alex
 *  @Date: 2020/10/26 15:55
 *  @version：V ROS 1.8.3
 *  @Description:
 */
@ApiModel(value = "修改状态", description = "修改状态")
@Data
@EqualsAndHashCode(callSuper = true)
public class ChanageStatusEnter extends GeneralEnter {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "状态")
    private Integer status;
}
