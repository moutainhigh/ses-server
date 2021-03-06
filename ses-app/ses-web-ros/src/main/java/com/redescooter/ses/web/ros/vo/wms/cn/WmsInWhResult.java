package com.redescooter.ses.web.ros.vo.wms.cn;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassNameWmsInWhResult
 * @Description
 * @Author Joan
 * @Date2020/7/17 16:43
 * @Version V1.0
 **/
@ApiModel(value = "仓储入库出参", description = "仓储入库出参")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class WmsInWhResult extends GeneralResult {

    @ApiModelProperty(value = "调拨/组装编号")
    private String allocateNumber;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "质检数")
    private Integer qty;

    @ApiModelProperty(value = "总数量")
    private Integer sumTotal;

    @ApiModelProperty(value = "收货人")
    private String consigneeId;

    @ApiModelProperty(value = "单据类型")
    private String documentType;

    @ApiModelProperty(value = "收货人姓")
    private String consigneelastName;

    @ApiModelProperty(value = "收货人名")
    private String consigneeFristName;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    @ApiModelProperty(value = "入库时间")
    private Date inWhTime;

    @ApiModelProperty(value = "Tale类型")
    private String classType;

    @ApiModelProperty(value = "单据类型")
    private String orderType;


}
