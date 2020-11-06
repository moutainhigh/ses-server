package com.redescooter.ses.mobile.rps.vo.restproductionorder.consign;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import lombok.*;

import io.swagger.annotations.*;

/**
 * @ClassName:SaveLogisticsOrderEnter
 * @description: SaveLogisticsOrderEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/11/06 14:31 
 */
@ApiModel(value = "保存物流运输单", description = "保存物流运输单")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
public class SaveLogisticsOrderEnter extends GeneralEnter {

    @ApiModelProperty(value = "委托单Id")
    private Long entrustId;

    @ApiModelProperty(value = "单号")
    private String logisticsNo;

    @ApiModelProperty(value = "物流公司")
    private String logisticsCompany;

    @ApiModelProperty(value = "备注")
    private String remark;
}
