package com.redescooter.ses.web.ros.vo.restproductionorder.consignorder;

import com.redescooter.ses.api.common.vo.base.GeneralResult;

import lombok.*;

/**
 *  @author: alex
 *  @Date: 2020/10/22 14:01
 *  @version：V ROS 1.8.3
 *  @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class ConsignOrderDetailResult extends GeneralResult {

    private Long id;
}
