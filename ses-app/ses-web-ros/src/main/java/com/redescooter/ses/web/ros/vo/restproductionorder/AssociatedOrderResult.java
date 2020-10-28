package com.redescooter.ses.web.ros.vo.restproductionorder;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 *  @author: alex
 *  @Date: 2020/10/26 14:13
 *  @version：V ROS 1.8.3
 *  @Description:
 */
@ApiModel(value = "关联订单的列表", description = "关联订单的列表")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AssociatedOrderResult extends GeneralResult {
    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "订单编号")
    private String orderNo;

    @ApiModelProperty(value = "订单类型")
    private Integer orderType;

    @ApiModelProperty(value = "订单创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Date createdDate;
}
