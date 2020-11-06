package com.redescooter.ses.mobile.rps.vo.restproductionorder.consign;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import lombok.*;

import io.swagger.annotations.*;

/**
 * @ClassName:ConsignShipEnter
 * @description: ConsignShipEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/11/03 19:33 
 */
@ApiModel(value = "出库单出库", description = "出库单出库")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
public class ConsignShipEnter extends GeneralEnter {

    @ApiModelProperty(value = "子单据Id")
    private Long id;

    @ApiModelProperty(value = "物流公司")
    private String logisticsCompany;

    @ApiModelProperty(value = "物流单号")
    private String shipmentN;

    @ApiModelProperty(value = "产品信息 ConsignShipProductEnter json 格式：[{\"id\":123,\"serialN\":\"3424242\",\"qty\":123}]\n")
    private String st;
}
