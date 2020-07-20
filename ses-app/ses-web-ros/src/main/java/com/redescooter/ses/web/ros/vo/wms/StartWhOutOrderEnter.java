package com.redescooter.ses.web.ros.vo.wms;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;

/**
 * @ClassName:StartWhOutOrderEnter
 * @description: StartWhOutOrderEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/07/20 11:56
 */
@ApiModel(value = "开始出库单入参", description = "开始出库单入参")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class StartWhOutOrderEnter extends GeneralEnter {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "物流方式")
    private String consignType;

    @ApiModelProperty(value = "空运方式")
    private String airParcelType;

    @ApiModelProperty(value = "收获公司")
    private String consignCompany;

    @ApiModelProperty(value = "物流单号")
    private String trackingN;

    @ApiModelProperty(value = "物流附件")
    private String annex;

    @ApiModelProperty(value = "物流金额")
    private String price;
}
