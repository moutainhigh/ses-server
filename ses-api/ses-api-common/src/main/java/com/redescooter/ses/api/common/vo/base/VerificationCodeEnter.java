package com.redescooter.ses.api.common.vo.base;

import lombok.*;

/**
 * @ClassNameverificationCodeEnter
 * @Description
 * @Author Joan
 * @Date2020/8/12 13:30
 * @Version V1.0
 **/
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class VerificationCodeEnter extends GeneralEnter {
  String code;
}
