package com.redescooter.ses.web.ros.vo.restproductionorder.inwhouse;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @ClassNameInWhouseSaveOrUpdateEnter
 * @Description
 * @Author Aleks
 * @Date2020/11/11 11:42
 * @Version V1.0
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "入库单新增或修改传参",description = "入库单新增或修改传参")
public class InWhouseSaveOrUpdateEnter extends GeneralEnter {

    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("入库类型，1：生产入库，2：返修入库，3：采购入库，4：退料入库，5：其他")
    private Integer inWhType;

    @ApiModelProperty("关联的单据号id")
    private Long relationOrderId;

    @ApiModelProperty("关联的单据号")
    private String relationOrderNo;

    @ApiModelProperty("入库单据类型,1:车辆，2:组装件，3:部件")
    private Integer orderType;

    @ApiModelProperty(value = "入库仓库。1:成品库，2:原料库，3:不合格品库")
    private Integer whType;

    @ApiModelProperty(value = "国家类型，1:中国，2:法国")
    private Integer countryType;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("入库明细")
    private String st;

}
