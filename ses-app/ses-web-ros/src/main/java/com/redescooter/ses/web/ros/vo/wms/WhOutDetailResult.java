package com.redescooter.ses.web.ros.vo.wms;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @ClassName:WhOutDetailResult
 * @description: WhOutDetailResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/07/20 11:07
 */
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class WhOutDetailResult extends GeneralResult {

    private Long id;

    private String whOutN;

    private Long goalWhId;

    private String goalWhName;

    private String address;

    private String consigneeFirstName;

    private String consigneeLastName;

    private String telephone;

    private String countryCode;

    private String email;

    private Long notifyId;

    private String notifyFirstName;

    private String notifyLastName;

    private String consignType;

    private String consignModel;

    private String consignCompany;

    private String trackingN;

    private String annex;

    private String price;
}
