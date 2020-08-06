package com.redescooter.ses.api.common.enums.wms;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum WhInTypeEnums {
  TODO("TODO", "待办事项", "1"),
  HISTORY("HISTORY", "历史", "2");

  private String code;

  private String message;

  private String value;

  public static String checkCode(String value) {
    for (WhOutClassTypeEnums item : WhOutClassTypeEnums.values()) {
      if (item.getValue().equals(value)) {
        return item.getValue();
      }
    }
    return null;
  }

  public static WhInTypeEnums getEnumByValue(String value) {
    for (WhInTypeEnums item : WhInTypeEnums.values()) {
      if (item.getValue().equals(value)) {
        return item;
      }
    }
    return null;
  }
}
