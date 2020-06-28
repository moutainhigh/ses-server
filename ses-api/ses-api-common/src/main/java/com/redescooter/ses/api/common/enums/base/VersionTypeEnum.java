package com.redescooter.ses.api.common.enums.base;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum VersionTypeEnum {

  REDE_RPS("REDE_RPS", "1", "rps"),
  REDE_SINGLECHIP("REDE_SINGLECHIP", "2", "单片机"),
  ;
  //系统编码
  private String VersionType;

  //编码对应值
  private String value;

  //编码备注说明
  private String remark;
}
