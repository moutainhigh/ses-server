package com.redescooter.ses.web.ros.vo.production.allocate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.redescooter.ses.api.common.constant.DateConstant;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @ClassName:AllocateOrderResult
 * @description: AllocateOrderResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/19 15:14
 */
@ApiModel(value = "调拨单", description = "调拨单")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class AllocateOrderResult extends GeneralResult {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "调拨单号")
    private String allocateN;

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "数量")
    private Integer qty;

    @ApiModelProperty(value = "创建人Id")
    private Long createdId;

    @ApiModelProperty(value = "创建人")
    private String createdFirstName;

    @ApiModelProperty(value = "创建人")
    private String createdLastName;

    @ApiModelProperty(value = "创建人")
    private String createdFullName;

    @ApiModelProperty(value = "收货人Id")
    private Long consigneeId;

    @ApiModelProperty(value = "收货人")
    private String consigneeFirstName;

    @ApiModelProperty(value = "收货人")
    private String consigneeLastName;

    @ApiModelProperty(value = "收货人")
    private String consigneeFullName;

    @ApiModelProperty(value = "收货人电话")
    private String consigneePhoneCountryCode;

    @ApiModelProperty(value = "收货人电话")
    private String consigneePhone;

    @ApiModelProperty(value = "收货人邮箱")
    private String consigneeEmail;

    @ApiModelProperty(value = "创建时间")
    @DateTimeFormat(pattern = DateConstant.DEFAULT_DATETIME_FORMAT)
    @JsonFormat(pattern = DateConstant.DEFAULT_DATETIME_FORMAT, timezone = DateConstant.UTC)
    private Date createdTime;
}
