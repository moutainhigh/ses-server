package com.redescooter.ses.web.ros.vo.restproductionorder.consignorder;

import com.redescooter.ses.api.common.vo.base.PageEnter;
import lombok.*;

import io.swagger.annotations.*;

/**
 *  @author: alex
 *  @Date: 2020/10/22 13:58
 *  @version：V ROS 1.8.3
 *  @Description:
 */
@ApiModel(value = "委托单列表", description = "委托单列表")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class ConsignOrderListEnter extends PageEnter {

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "关键字")
    private String keyword;

}
