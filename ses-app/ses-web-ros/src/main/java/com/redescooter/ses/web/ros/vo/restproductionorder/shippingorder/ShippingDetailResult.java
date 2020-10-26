package com.redescooter.ses.web.ros.vo.restproductionorder.shippingorder;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import lombok.Data;

import lombok.*;

import java.util.Date;
import java.util.List;

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

    //发货人
    private Long consignorUserId;

    private String consignorUserFistName;

    private String consignorUserLastName;

    private String consignorUserCountryCode;

    private String consignorUserTelephone;

    private String consignorUserEmail;

    //收货人
    private Long consigneeUserId;

    private String consigneeUserFistName;

    private String consigneeUserLastName;

    private String consigneeUserCountryCode;

    private String consigneeUserTelephone;

    private String consigneeUserEmail;

    //通知人
    private Long notifyUserId;

    private String notifyUserFistName;

    private String notifyUserLastName;

    private String notifyUserCountryCode;

    private String notifyUserTelephone;

    private String notifyUserEmail;

    private String zipCode;

    private String address;

    private List<Object> invoiceProductList;

    private List<Object> payDetailList;
}
