package com.redescooter.ses.api.common.enums.production.purchasing;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName:PurchasingEventEnums
 * @description: PurchasingEventEnums
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/19 13:19
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum PurchasingEventEnums {

    PENDING("PENDING", "待确认", "1"),
    INPROGRESS("INPROGRESS", "进行中", "2"),
    MATERIALS_QC("MATERIALS_QC", "QC来料质检", "3"),
    QC_COMPLETED("QC_COMPLETED", "质检完成", "4"),
    RETURN("RETURN", "退货并完成", "5"),
    IN_PURCHASING_WH("IN_PURCHASING_WH", "入库", "6"),
    CANCELLED("CANCELLED", "取消", "7");

    private String code;

    private String message;

    private String value;
}
