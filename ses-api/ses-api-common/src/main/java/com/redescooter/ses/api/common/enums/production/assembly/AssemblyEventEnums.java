package com.redescooter.ses.api.common.enums.production.assembly;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName:AssemblyEventEnums
 * @description: AssemblyEventEnums
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/31 16:04
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum AssemblyEventEnums {

    PENDING("PENDING", "待处理", "1"),
    PREPARE_MATERIAL("PREPARE_MATERIAL", "组装备料", "2"),
    ASSEMBLING("ASSEMBLING", "组装中", "3"),
    QC("QC", "质检中", "4"),
    QC_PASSED("QC_PASSED", "质检通过", "5"),
    IN_PRODUCTION_WH("IN_PRODUCTION_WH", "入库", "6"),
    CANCELLED("CANCELLED", "取消", "7");

    private String code;

    private String message;

    private String value;
}
