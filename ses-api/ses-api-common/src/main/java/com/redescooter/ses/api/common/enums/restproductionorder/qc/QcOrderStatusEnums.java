package com.redescooter.ses.api.common.enums.restproductionorder.qc;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Description 质检单状态
 * @Author Chris
 * @Date 2021/1/25 10:45
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum QcOrderStatusEnums {

    TO_BE_QC("TO_BE_QC", "待质检", 1),

    QCING("QCING", "质检中", 10),

    QC_FINISH("QC_FINISH", "质检完成", 20);

    private String code;

    private String message;

    private Integer value;

}
