package com.redescooter.ses.web.ros.vo.wms;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Date;

/**
 * @ClassNameWmsWhInStockPendingResult
 * @Description
 * @Author Joan
 * @Date2020/7/17 16:52
 * @Version V1.0
 **/
@ApiModel(value = "仓储库存待定结果入参", description = "仓储库存待定结果入参")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class WmsWhInStockPendingResult extends GeneralResult {
  /**
   * 调拨/组装编号
   */
  @ApiModelProperty(value = "调拨/组装编号")
  private String allocateNumber;


  /**
   * 质检数
   */
  @ApiModelProperty(value = "质检数")
  private Integer qty;

  /**
   * 委托人
   */
  @ApiModelProperty(value = "委托人")
  private String consignee;

  /**
   * 入库时间
   */
  @ApiModelProperty(value = "创建时间")
  private Date CreationTime;


}
