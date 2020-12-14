package com.redescooter.ses.web.ros.vo.restproductionorder.assembly;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.AssociatedOrderResult;
import com.redescooter.ses.web.ros.vo.restproductionorder.optrace.OpTraceResult;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Date;
import java.util.List;

import io.swagger.annotations.*;

/**
 * @ClassName:ProductionAssemblyOrderDetailResult
 * @description: ProductionAssemblyOrderDetailResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/11/11 14:05 
 */
@ApiModel(value = "采购单详情出参", description = "采购单详情出参")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
public class ProductionAssemblyOrderDetailResult extends GeneralResult {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "单据类型")
    private Integer combinType;

    @ApiModelProperty(value = "页面类型 1 车辆 2 组装")
    private Integer classType;

    @ApiModelProperty(value = "订单编号")
    private String combinNo;

    @ApiModelProperty(value = "订单状态")
    private Integer combinStatus;

    @ApiModelProperty(value = "组装开始时间")
    private Date combinStartDate;

    @ApiModelProperty(value = "组装结束时间")
    private Date combinEndDate;

    @ApiModelProperty(value = "负责人")
    private Long principalId;

    @ApiModelProperty(value = "负责人名字")
    private String principalName;

    @ApiModelProperty(value = "国家代码")
    private String countryCode;

    @ApiModelProperty(value = "电话")
    private String telephone;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "产品列表")
    private List<AssemblyDetailProductListResult> productList;

    @ApiModelProperty(value = "关联订单")
    private List<AssociatedOrderResult> associatedOrderResultList;

    @ApiModelProperty(value = "操作动态")
    private List<OpTraceResult> operatingDynamicsList;

}
