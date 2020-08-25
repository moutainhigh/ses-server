package com.redescooter.ses.web.ros.enums.sellsy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName:SellsyMethodType
 * @description: SellsyMethodType
 * @author: Alex
 * @Version：1.3
 * @create: 2020/08/19 17:13
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum SellsyMethodTypeEnums {
    
    UPDATE("UPDATE","修改","update"),
    QUERY("QUERY","查询","query"),
    ADD("","添加","add"),
    DELETE("DELETE","删除","delete");
    
    private String code;
    
    private String message;
    
    private String value;
    
}
