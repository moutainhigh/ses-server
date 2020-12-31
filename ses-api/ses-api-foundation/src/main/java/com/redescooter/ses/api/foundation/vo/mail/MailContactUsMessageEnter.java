package com.redescooter.ses.api.foundation.vo.mail;

import com.redescooter.ses.api.common.vo.base.BaseMailTaskEnter;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

/**
 * @ClassNameMailContactUsReplyMessageEnter
 * @Description
 * @Author Joan
 * @Date2020/8/12 18:17
 * @Version V1.0
 **/
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class MailContactUsMessageEnter extends BaseMailTaskEnter {

  @ApiModelProperty("消息")
  private String message;

  @ApiModelProperty("")
  private List<String> imgList;


}


