package com.redescooter.ses.web.ros.vo.production.purchasing;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;

/**
 * @ClassName:PurchasingNodeResult
 * @description: PurchasingNodeResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/18 18:01
 */
@ApiModel(value = "节点列表", description = "节点列表")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
public class PurchasingNodeResult extends GeneralResult {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "事件")
    private String event;

    @ApiModelProperty(value = "事件时间")
    private String eventTime;

    @ApiModelProperty(value = "创建人")
    private Long createdBy;

    @ApiModelProperty(value = "创建人")
    private String createdByFirstName;

    @ApiModelProperty(value = "创建人")
    private String createdByLastName;
}
