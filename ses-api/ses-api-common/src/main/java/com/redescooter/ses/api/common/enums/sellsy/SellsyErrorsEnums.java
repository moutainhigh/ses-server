package com.redescooter.ses.api.common.enums.sellsy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName:SellsyErrorsEnums
 * @description: SellsyErrorsEnums
 * @author: Alex
 * @Version：1.3
 * @create: 2020/08/19 20:13
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum SellsyErrorsEnums {
    
    E_USER_NOT_LOGGED("E_USER_NOT_LOGGED", "User is not connected", "1"),
    E_IO_MODE_DONT_EXIST("E_IO_MODE_DONT_EXIST", "Mode input / output does not exist", "2"),
    E_IO_MODE_DO_IN_MISSING("E_IO_MODE_DO_IN_MISSING", "Mode input / output is missing", "3"),
    E_DO_IN_WRONG_FORMAT("E_DO_IN_WRONG_FORMAT", "The input format is wrong", "3"),
    E_METHOD_DONT_EXIT("E_METHOD_DONT_EXIT", "The method specified does not exist", "4"),
    E_DO_IN_PARAM_MISSING("E_DO_IN_PARAM_MISSING", "DO_IN parameter is missing", "5"),
    E_PRIV_NOT_ALLOWED("E_PRIV_NOT_ALLOWED", "The user does not have privileges to access this resource", "6"),
    E_SUBSCRIBE_HAVETO("E_SUBSCRIBE_HAVETO", "The user needs a subscription to access this resource", "7"),
    E_PARAM_MISSING("E_PARAM_MISSING", "Parameter is missing", "8"),
    E_PARAM_INVALID("E_PARAM_INVALID", "The parameter is invalid", "9"),
    E_PARAM_REQUIRED("E_PARAM_REQUIRED", "Parameter is missing or incorrect and it is obligatory", "10"),
    E_OBJ_NOT_LOADABLE("E_OBJ_NOT_LOADABLE", "An object is not loadable", "11"),
    E_OBJ_NOT_EDITABLE("E_OBJ_NOT_EDITABLE", "An object is not editable", "12"),
    E_OBJ_NOT_LOADED("E_OBJ_NOT_LOADED", "The object is not loaded", "13"),
    E_LIST_DONT_EXIST("E_LIST_DONT_EXIST", "The list does not exist", "14"),
    E_LIST_VALUE_DONT_EXIST("E_LIST_VALUE_DONT_EXIST", "The requested value does not exist in the list", "15"),
    E_PAGINATION_MAX("E_PAGINATION_MAX", "The parameter is incorrect pagination", "16"),
    E_UNKNOW("E_UNKNOW", "Unknown error", "17"),
    E_CUSTOM("E_CUSTOM", "Manual error", "18");
    
    private String code;
    
    private String message;
    
    private String value;
}
