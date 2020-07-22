package com.redescooter.ses.web.ros.vo.wms;

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
  private  String id;

  @ApiModelProperty(value = "产品/部品类型")
  private String productType;

  @ApiModelProperty(value = "质检数")
  private Integer qty;

  @ApiModelProperty(value = "收货人")
  private String consignee;


  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
  @ApiModelProperty(value = "入库时间")
  private Date inWhTime;


}
