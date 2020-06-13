package com.redescooter.ses.web.ros.vo.website;

import com.redescooter.ses.api.common.annotation.MinimumLength;
import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.annotation.Regexp;
import com.redescooter.ses.api.common.constant.RegexpConstant;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.ros.exception.ValidationExceptionCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @ClassNameGetEailEnter
 * @Description
 * @Author Joan
 * @Date2020/5/20 16:55
 * @Version V1.0
 **/
@ApiModel(value = "存储邮箱名称", description = "存储邮箱名称")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class StorageEamilEnter extends GeneralEnter {
  @ApiModelProperty(value = "邮箱")
  @NotNull(code = com.redescooter.ses.api.common.exception.ValidationExceptionCode.EMAIL_IS_EMPTY,message = "邮箱为空")
  @Regexp(value = RegexpConstant.email,code = ValidationExceptionCode.EMAIL_CHAR_IS_NOT_ILLEGAL,message = "邮箱非法")
  @MinimumLength(value = "2",code = ValidationExceptionCode.CHARACTER_IS_TOO_SHORT,message = "字符过短")
  private String email;
}
