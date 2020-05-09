package com.redescooter.ses.api.common.enums.production.assembly;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName:AssemblyStatusEnums
 * @description: AssemblyStatusEnums
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/31 15:56
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum AssemblyStatusEnums {

    PENDING("PENDING", "待处理", "1"),
    PREPARE_MATERIAL("PREPARE_MATERIAL", "组装备料", "2"),
    ASSEMBLING("ASSEMBLING", "开始组装", "3"),
    QC("QC", "开始质检", "4"),
    QC_PASSED("QC_PASSED", "质检通过", "5"),
    IN_PRODUCTION_WH("IN_PRODUCTION_WH", "入库", "6"),
    CANCELLED("CANCELLED", "取消", "7");

    private String code;

    private String message;

    private String value;
}
