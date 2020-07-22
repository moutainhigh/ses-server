package com.redescooter.ses.web.ros.vo.wms.cn;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;

/**
 * @ClassName:WhOutDetailResult
 * @description: WhOutDetailResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/07/20 11:07
 */
@ApiModel(value = "出库单详情", description = "出库单详情")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class WhOutDetailResult extends GeneralResult {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "出库单号")
    private String whOutN;

    @ApiModelProperty(value = "目标仓库Id")
    private Long goalWhId;

    @ApiModelProperty(value = "仓库名称")
    private String goalWhName;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "收获人Id")
    private String consigneeId;

    @ApiModelProperty(value = "收获人姓名")
    private String consigneeFirstName;

    @ApiModelProperty(value = "收获人姓名")
    private String consigneeLastName;

    @ApiModelProperty(value = "收获人电话")
    private String telephone;

    @ApiModelProperty(value = "收获人电话国家代码")
    private String countryCode;

    @ApiModelProperty(value = "收获人邮箱")
    private String email;

    @ApiModelProperty(value = "通知人Id")
    private Long notifyId;

    @ApiModelProperty(value = "通知人")
    private String notifyFirstName;

    @ApiModelProperty(value = "通知人")
    private String notifyLastName;

    @ApiModelProperty(value = "物流方式")
    private String consignType;

    @ApiModelProperty(value = "委托方式")
    private String consignMethod;

    @ApiModelProperty(value = "物流公司")
    private String consignCompany;

    @ApiModelProperty(value = "物流单号")
    private String trackingN;

    @ApiModelProperty(value = "附件")
    private String annex;

    @ApiModelProperty(value = "价格")
    private String price;
}
