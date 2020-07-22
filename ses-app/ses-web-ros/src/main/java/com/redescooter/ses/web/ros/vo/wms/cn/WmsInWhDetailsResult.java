package com.redescooter.ses.web.ros.vo.wms.cn;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

/**
 * @ClassNameWmsInWhDetailsResult
 * @Description
 * @Author Joan
 * @Date2020/7/20 11:33
 * @Version V1.0
 **/
@ApiModel(value = "入库单人员信息详情出参", description = "入库单人员信息详情出参")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class WmsInWhDetailsResult extends GeneralResult {

  @ApiModelProperty(value = "调拨/组装编号")
  private String allocateNumber;

  @ApiModelProperty(value = "收获人")
  private String consignee;

  @ApiModelProperty(value = "状态")
  private String status;

  @ApiModelProperty(value = "手机号码")
  private String phone;

  @ApiModelProperty(value = "邮箱")
  private String email;
}
