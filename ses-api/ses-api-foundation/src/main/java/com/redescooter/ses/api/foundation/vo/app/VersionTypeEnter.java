package com.redescooter.ses.api.foundation.vo.app;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.foundation.exception.ValidationExceptionCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @ClassNameAppTypeEnter
 * @Description
 * @Author Joan
 * @Date2020/6/17 14:02
 * @Version V1.0
 **/
@ApiModel(value = "应用版本参数", description = "查询条件")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class VersionTypeEnter extends GeneralEnter {
 
  @ApiModelProperty(value = "1.rps  2.singlechip")
  @NotNull(code = ValidationExceptionCode.CLIENT_TYPE_IS_EMPTY,message = "客户端类型为空")
  private Integer type;

  @ApiModelProperty(value = "应用code")
  @NotNull(code = ValidationExceptionCode.CLIENT_CODE_IS_EMPTY,message = "客户端代码为空")
  private String code;

}
