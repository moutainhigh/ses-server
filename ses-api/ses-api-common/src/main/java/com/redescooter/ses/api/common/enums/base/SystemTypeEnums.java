package com.redescooter.ses.api.common.enums.base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Admin
 */

@Getter
@AllArgsConstructor
public enum SystemTypeEnums {
  REDE_ROS("REDE_ROS", "1", "ROS"),
  REDE_SAAS("REDE_SAAS", "2", "SAAS"),
  ;
  //系统编码
  private String code;

  //编码对应值
  private String value;

  //编码备注说明
  private String message;

  public static SystemTypeEnums getSystemTypeEnumsByCode(String code) {
      for (SystemTypeEnums item : SystemTypeEnums.values()) {
          if (StringUtils.equals(item.getCode(), code)) {
              return item;
          }
      }
      return null;
  }
}
