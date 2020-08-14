package com.redescooter.ses.web.ros.vo.customer;

import com.redescooter.ses.api.common.annotation.MaximumLength;
import com.redescooter.ses.api.common.annotation.MinimumLength;
import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.ros.exception.ValidationExceptionCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @ClassNameContactUsMessageEnter
 * @Description
 * @Author Joan
 * @Date2020/8/12 16:18
 * @Version V1.0
 **/
@ApiModel(value = "联系我们发送消息入参", description = "联系我们发送消息入参")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class ContactUsMessageEnter extends GeneralEnter {
  @ApiModelProperty("主键id")
  @NotNull(code = com.redescooter.ses.api.common.exception.ValidationExceptionCode.ID_IS_EMPTY, message = "主键不能为空")
  private Long id;

  @ApiModelProperty("消息")
  @NotNull(code = ValidationExceptionCode.MESSAGE_IS_EMPTY, message = "消息不能为空")
  @MaximumLength(value = "2000",code = ValidationExceptionCode.MESSAGE_IS_ILLEGAL,message = "消息非法")
  private String message;
}
