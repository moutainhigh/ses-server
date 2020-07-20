package com.redescooter.ses.web.ros.vo.wms;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

import io.swagger.annotations.*;

/**
 * @ClassName:WhOutListResult
 * @description: WhOutListResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/07/20 10:30
 */
@ApiModel(value = "出库单列表", description = "出库单列表")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class WhOutOrderListResult extends GeneralResult {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "出库单号")
    private String whOutN;

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "运输方式")
    private String consignType;

    @ApiModelProperty(value = "物流公司")
    private String consignCompany;

    @ApiModelProperty(value = "物流单号")
    private String trackingN;

    @ApiModelProperty(value = "数量")
    private int qty;

    @ApiModelProperty(value = "创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Date createdTime;
}
