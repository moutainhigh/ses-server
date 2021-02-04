package com.redescooter.ses.api.common.enums.restproductionorder.assembly;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Description
 * @Author Chris
 * @Date 2021/1/25 18:35
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum NewCombinOrderStatusEnums {

    DRAF("DRAF","草稿",1),

    PREPARED("PREPARED","待备料",10),

    PREPARATION_COMPLETED("PREPARATION_COMPLETED","备料完成",20),

    ASSEMBLING("ASSEMBLING","组装中",30),

    ASSEMBL_FINISH("ASSEMBL_FINISH", "组装完成", 40),

    INSPECTING("INSPECTING", "质检中", 45),

    QC_FINISH("QC_FINISH", "质检完成", 50);

    private String code;

    private String message;

    private Integer value;

}
