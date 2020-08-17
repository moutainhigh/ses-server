package com.redescooter.ses.api.common.enums.wms;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum WmsStockClassTypeEnums {
  AVAILABLE_ONE("AVAILABLE_ZERO","显示库存","1"),

  TOBEPREDICTED_TWO("TOBEPREDICTED_ONE","待生产","2"),

  TOBESTORED_THREE("TOBESTORED_TWO","待入库","3"),

  OUTWH_FOUR("OUTWH_THREE","已出库","4")
  ;

  private String code;

  private String message;

  private String value;

}
