package com.redescooter.ses.web.ros.vo.restproductionorder.assembly;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import lombok.*;

import java.util.Date;

import io.swagger.annotations.*;
import org.springframework.format.annotation.DateTimeFormat;

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

    @ApiModelProperty(value = "组装单类型")
    private Integer combinType;

    @ApiModelProperty(value = "订单编号")
    private String combinNo;

    @ApiModelProperty(value = "状态")
    private Integer combinStatus;

    @ApiModelProperty(value = "数量")
    private Integer combinQty;

    @ApiModelProperty(value = "开始时间")
    private Date combinStartDate;

    @ApiModelProperty(value = "结束时间")
    private Date combinEndDate;

    @ApiModelProperty(value = "负责人Id")
    private Long principalId;

    @ApiModelProperty(value = "负责人姓名")
    private String principalName;

    @ApiModelProperty(value = "负责人国家代码")
    private String countryCode;

    @ApiModelProperty(value = "负责人电话")
    private String telephone;

    @ApiModelProperty(value = "创建人Id")
    private Long createById;

    @ApiModelProperty(value = "创建人名字")
    private String createByFistName;

    @ApiModelProperty(value = "创建人名字")
    private String createByLastName;

    @ApiModelProperty(value = "创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Date createdTime;
}
