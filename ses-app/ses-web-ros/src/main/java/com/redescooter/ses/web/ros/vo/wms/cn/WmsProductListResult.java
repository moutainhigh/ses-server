package com.redescooter.ses.web.ros.vo.wms.cn;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @ClassNameProductListResult
 * @Description
 * @Author Joan
 * @Date2020/7/20 11:39
 * @Version V1.0
 **/
@ApiModel(value = "入库部件/产品信息详情出参", description = "入库部件/产品信息详情出参")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class WmsProductListResult extends GeneralResult {


  @ApiModelProperty(value = "产品/部品编号")
  private String productNumber;

  @ApiModelProperty(value = "产品/部品类型")
  private String productType;

  @ApiModelProperty(value = "质检数")
  private Integer qty;

  @ApiModelProperty(value = "中文名称")
  private String cnName;

  @ApiModelProperty(value = "英文名称")
  private String enName;

}
