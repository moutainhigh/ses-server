package com.redescooter.ses.api.common.enums.base;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum VersionTypeEnum {


  REDE_SCS("REDE_SCS", "1", "scs"),

  REDE_SINGLECHIP("REDE_SINGLECHIP", "2", "单片机"),

  REDE_RPS("REDE_RPS", "3", "rps"),

  REDE_ANDROID("REDE_ANDROID", "4", "android系统"),

  REDE_IOS("REDE_IOS", "5", "ios系统"),

  ;
  //系统编码
  private String VersionType;

  //编码对应值
  private String value;

  //编码备注说明
  private String remark;
}
