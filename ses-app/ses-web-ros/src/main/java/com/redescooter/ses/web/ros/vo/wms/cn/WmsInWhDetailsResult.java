package com.redescooter.ses.web.ros.vo.wms.cn;

import com.redescooter.ses.api.common.annotation.NotNull;
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
  @ApiModelProperty(value = "id")
  private  String id;

  @ApiModelProperty(value = "调拨/组装编号")
  private String allocateNumber;

  @ApiModelProperty(value = "电话国家代码")
  private String telCountryCode;

  @ApiModelProperty(value = "收货人")
  private String consigneeId;

  @ApiModelProperty(value = "收货人姓")
  private String consigneelastName;

  @ApiModelProperty(value = "收货人名")
  private String consigneeFristName;

  @ApiModelProperty(value = "类型")
  private String classType;

  @ApiModelProperty(value = "状态")
  private String status;

  @ApiModelProperty(value = "手机号码")
  private String phone;

  @ApiModelProperty(value = "邮箱")
  private String email;
}
