package com.redescooter.ses.web.ros.vo.wms.cn;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @ClassNamewmsStockTypeResult
 * @Description
 * @Author Joan
 * @Date2020/7/22 15:50
 * @Version V1.0
 **/
@ApiModel(value = "库存类型出参", description = "库存类型出参")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class WmsStockTypeResult extends GeneralEnter {
  @ApiModelProperty(value = "key值")
  private String key;

  @ApiModelProperty(value = "value值")
  private Integer value;
}
