package com.redescooter.ses.api.common.enums.wms;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/**
 * @ClassName:ConsignType
 * @description: ConsignType
 * @author: Alex
 * @Version：1.3
 * @create: 2020/07/20 14:28
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ConsignTypeEnums {

    AIR_PARCEL("AIR_PARCEL","空运","1"),
    SEA_TRANSPORTATION("SEA_TRANSPORTATION","海运","2"),
    LAND_CARRIAGE("LAND_CARRIAGE","陆运","3");

    private String code;

    private String message;

    private String value;

    public static ConsignTypeEnums getEnumsByCode(String code){
        for (ConsignTypeEnums value : ConsignTypeEnums.values()) {
            if (StringUtils.equals(value.code,code)){
                return value;
            }
        }
        return null;
    }
    public static ConsignTypeEnums getEnumsByValue(String value){
        for (ConsignTypeEnums item : ConsignTypeEnums.values()) {
            if (StringUtils.equals(item.getValue(),value)){
                return item;
            }
        }
        return null;
    }
}
