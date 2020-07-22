package com.redescooter.ses.web.ros.vo.wms.cn;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.math.BigDecimal;

/**
 * @ClassNameWmsStockPredictedResult
 * @Description
 * @Author Joan
 * @Date2020/7/17 15:43
 * @Version V1.0
 **/
@ApiModel(value = "仓储待生产出参", description = "仓储待生产出参")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class WmsStockResult extends GeneralEnter {
  @ApiModelProperty(value = "id")
  private  String id;

  @ApiModelProperty(value = "产品/部品编号")
  private String productNumber;

  @ApiModelProperty(value = "质检数")
  private Integer qty;


  @ApiModelProperty(value = "中文名称")
  private String cnName;


  @ApiModelProperty(value = "英文名称")
  private String enName;


  @ApiModelProperty(value="价格 浮点型价格")
  private String price;
}
