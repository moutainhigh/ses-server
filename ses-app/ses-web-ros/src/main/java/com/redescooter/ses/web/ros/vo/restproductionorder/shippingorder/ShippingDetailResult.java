package com.redescooter.ses.web.ros.vo.restproductionorder.shippingorder;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import lombok.Data;

import lombok.*;

import java.util.Date;

/**
 *  @author: alex
 *  @Date: 2020/10/26 11:39
 *  @version：V ROS 1.8.3
 *  @Description:
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ShippingDetailResult extends GeneralResult {

    private Long id;

    private String invoiceNo;

    private String invoiceStatus;

    private Date deliveryDate;

    private Long consignorUserId;

    private String consignorUserFistName;

    private String consignorUserLastName;
}
