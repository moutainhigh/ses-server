package com.redescooter.ses.web.ros.vo.restproductionorder.outboundorder;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import lombok.*;

/**
 *  @author: alex
 *  @Date: 2020/10/22 13:52
 *  @version：V ROS 1.8.3
 *  @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class OutboundOrderDetailResult extends GeneralResult {

    private Long id;
}
