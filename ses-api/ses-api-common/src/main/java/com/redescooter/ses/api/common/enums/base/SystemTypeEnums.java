package com.redescooter.ses.api.common.enums.base;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Admin
 */

@Getter
@AllArgsConstructor
public enum SystemTypeEnums {
  REDE_IOS("REDE_IOS", "1", "ios系统"),
  REDE_ANDROID("REDE_ANDROID", "2", "android系统"),
  ;
  //系统编码
  private String systemType;

  //编码对应值
  private String value;

  //编码备注说明
  private String remark;
}
