package com.redescooter.ses.api.common.enums.restproductionorder.assembly;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName:CombinOrderStatusEnums
 * @description: CombinOrderStatusEnums
 * @author: Alex
 * @Version：1.3
 * @create: 2020/11/12 14:04 
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum CombinOrderStatusEnums {

    DRAF("DRAF","草稿",1),
    PREPARED("PREPARED","待备料",10),
    PREPARATION_COMPLETED("PREPARATION_COMPLETED","备料完成",20),
    TO_BE_ASSEMBLED("TO_BE_ASSEMBLED","待组装",30),
    ASSEMBLING("ASSEMBLING","组装中",40),
    WAIT_QC("WAIT_QC","待质检",50),;



    private String code;

    private String message;

    private Integer value;
}
