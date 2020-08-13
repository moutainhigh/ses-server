package com.redescooter.ses.web.ros.vo.wms.cn;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.exception.ValidationExceptionCode;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.PageEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @ClassNameWmsWhInDetailsEnter
 * @Description
 * @Author Joan
 * @Date2020/7/20 14:13
 * @Version V1.0
 **/
@ApiModel(value = "待入库列表详情入参", description = "待入库列表详情入参")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class WmsWhInDetailsEnter extends PageEnter {
  @ApiModelProperty(value = "id",required = true)
  @NotNull(code = ValidationExceptionCode.ID_IS_EMPTY, message = "id 为空")
  private  long id;

  @ApiModelProperty(value = "类型")
  private String classType;

  @ApiModelProperty(value = "单据类型",required = true)
  @NotNull(code = com.redescooter.ses.web.ros.exception.ValidationExceptionCode.TYPE_IS_EMPTY, message = "类型 为空")
  private String documentType;
}
