package com.redescooter.ses.web.ros.vo.wms;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.math.BigDecimal;

/**
 * @ClassNameWmsUsableResult
 * @Description
 * @Author Joan
 * @Date2020/7/16 18:55
 * @Version V1.0
 **/
@ApiModel(value = "仓储可用出参", description = "仓储可用出参")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class WmsStockAvailableResult extends GeneralResult {
  /**
   * 产品编号
   */
  @ApiModelProperty(value = "产品/部品编号")
  private String productNumber;


  /**
   * 入库总数
   */
  @ApiModelProperty(value = "入库总数")
  private Integer intTotal;

  /**
   * 中文名称
   */
  @ApiModelProperty(value = "中文名称")
  private String cnName;

  /**
   * 英文名称
   */
  @ApiModelProperty(value = "英文名称")
  private String enName;

  /**
   * 价格 浮点型价格
   */
  @ApiModelProperty(value="价格 浮点型价格")
  private BigDecimal price;

}