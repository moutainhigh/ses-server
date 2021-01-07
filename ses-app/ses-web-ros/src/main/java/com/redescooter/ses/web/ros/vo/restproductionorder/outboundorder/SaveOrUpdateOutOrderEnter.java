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

    @ApiModelProperty(value = "关联的单据id")
    private Long relationId;

    @ApiModelProperty(value = "关联的单据号")
    private String relationNo;

    @ApiModelProperty(value = "关联的单据类型，3：发货单，9：组装单")
    private Integer relationType;

    @ApiModelProperty(value = "出库单类型，1：整车，2：组装件，3：部件")
    private Integer outWhType;

    @ApiModelProperty(value = "出库类型，1：销售调拨,2：生产组装")
    private Integer outType;

    @ApiModelProperty(value = "出库仓库。1:成品库，2:原料库，3:不合格品库")
    private Integer whType;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "国家类型，1:中国，2:法国")
    private Integer countryType;

    @ApiModelProperty("出库明细")
    private String st;

}
