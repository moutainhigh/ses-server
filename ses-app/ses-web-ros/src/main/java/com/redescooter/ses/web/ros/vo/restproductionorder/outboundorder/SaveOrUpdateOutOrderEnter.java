package com.redescooter.ses.web.ros.vo.restproductionorder.outboundorder;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @description:
 * @author: Aleks
 * @create: 2021/01/04 15:13
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("186版本新增或编辑出库单的传参")
public class SaveOrUpdateOutOrderEnter extends GeneralEnter {

    @ApiModelProperty(value = "主键id")
    private Long id;

    @ApiModelProperty("关联的单据号id")
    private Long relationOrderId;

    @ApiModelProperty("关联的单据号")
    private String relationOrderNo;

    @ApiModelProperty(value = "关联的单据类型，3：发货单，9：组装单，10:退换单")
    private Integer relationOrderType;

    @ApiModelProperty(value = "出库单类型，1：整车，2：组装件，3：部件")
    private Integer outWhType;

    @ApiModelProperty(value = "出库类型，1：调拨出库，2：组装备料出库，3：退换出库，4：其它，5:返修出库，6:退货出库，7:销售出库")
    private Integer outType;

    @ApiModelProperty(value = "出库仓库。1:成品库，2:原料库，3:不合格品库")
    private Integer whType;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "国家类型，1:中国，2:法国")
    private Integer countryType;

    @ApiModelProperty(value = "是否是不合格品库产生，0:否，1:是")
    private Integer source = 0;

    @ApiModelProperty("出库明细")
    private String st;

    @ApiModelProperty("是否是仓库新增，0：否，1：是")
    private Integer ifWh = 0;

}
