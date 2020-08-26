package com.redescooter.ses.web.ros.enums.sellsy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName:SellsyDocumentRosEnums
 * @description: SellsyDocumentRosEnums
 * @author: Alex
 * @Version：1.3
 * @create: 2020/08/26 15:17
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum SellsyDocumentRosTypeEnums {
    
    PACKAGING("1","第一行","packaging"),
    SHIPPING("2","第二行","shipping"),
    ITEM("3","第三行","item"),
    ONCE("4","第四行","once"),
    SUM("5","第五行","sum"),
    TITLE("6","第六行","title"),
    COMMENT("7","第七行","comment"),
    BREAK("8","第八行","break"),
    EMPTY("9","第九行","empty"),
    ;
    
    private String code;
    
    private String message;
    
    private String value;
}
