package com.redescooter.ses.mobile.rps.vo.printentry;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.redescooter.ses.api.common.constant.DateConstant;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 *  @author: alex
 *  @Date: 2020/10/23 14:32
 *  @version：V ROS 1.8.3
 *  @Description:
 */
@Data
public class PrintEntryResult extends GeneralResult {
    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "订单创建时间")
    @DateTimeFormat(pattern = DateConstant.DEFAULT_DATETIME_FORMAT)
    @JsonFormat(pattern = DateConstant.DEFAULT_DATETIME_FORMAT, timezone = DateConstant.UTC)
    private Date createdTime;

    @ApiModelProperty(value = "订单编号")
    private String purchasN;

    @ApiModelProperty(value = "部件总数")
    private int partqty;
}
