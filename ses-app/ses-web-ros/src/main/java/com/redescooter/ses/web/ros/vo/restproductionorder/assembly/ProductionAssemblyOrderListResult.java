package com.redescooter.ses.web.ros.vo.restproductionorder.assembly;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import lombok.*;

import java.util.Date;

import io.swagger.annotations.*;

/**
 * @ClassName:AssemblyPurchasOrderListResult
 * @description: AssemblyPurchasOrderListResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/11/10 14:11 
 */
@ApiModel(value = "", description = "")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
public class ProductionAssemblyOrderListResult extends GeneralResult {
    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "订单编号")
    private String orderNo;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "数量")
    private Integer qty;

    @ApiModelProperty(value = "开始时间")
    private Date startDate;

    @ApiModelProperty(value = "结束时间")
    private Date endDate;

    @ApiModelProperty(value = "负责人Id")
    private Long principalId;

    @ApiModelProperty(value = "负责人姓")
    private String principalFirstName;

    @ApiModelProperty(value = "负责人名")
    private String principalLastName;

    @ApiModelProperty(value = "负责人国家代码")
    private String principalCountryCode;

    @ApiModelProperty(value = "负责人电话")
    private String principalTelephone;

    @ApiModelProperty(value = "创建人Id")
    private Long createById;

    @ApiModelProperty(value = "创建人名字")
    private String createByFistName;

    @ApiModelProperty(value = "创建人名字")
    private String createByLastName;

    @ApiModelProperty(value = "创建时间")
    private Date createDate;
}
