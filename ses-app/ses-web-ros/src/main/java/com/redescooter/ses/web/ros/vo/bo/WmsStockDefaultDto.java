package com.redescooter.ses.web.ros.vo.bo;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.vo.base.PageEnter;
import com.redescooter.ses.web.ros.exception.ValidationExceptionCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @ClassNamewmsEnter
 * @Description
 * @Author Joan
 * @Date2020/7/16 18:54
 * @Version V1.0
 **/
@ApiModel(value = "库存默认入参", description = "库存默认入参")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class WmsStockDefaultDto extends PageEnter {
  @ApiModelProperty(value = "零部件")
  private String parts;

  @ApiModelProperty(value="配件")
  private String accessory;

  @ApiModelProperty(value = "电池")
  private String battery;

  @ApiModelProperty(value="整车")
  private String scooter;

  @ApiModelProperty(value="组合")
  private String combination;

  @ApiModelProperty(value="法国销售金额")
  private String fr;

  @ApiModelProperty(value="采购")
  private long purchas;

  @ApiModelProperty(value="组装")
  private long assembly;
}
