package com.redescooter.ses.api.common.enums.restproductionorder.productionpurchas;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName:ProductionPurchasEnums
 * @description: ProductionPurchasEnums
 * @author: Alex
 * @Version：1.3
 * @create: 2020/11/11 11:46 
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ProductionPurchasEnums {

    DRAFT("DRAFT", "草稿", 1),

    PURCHASING("PURCHASING", "采购中", 10),

    TO_BE_STORED("TO_BE_STORED", "待入库", 20),

    PARTIAL_STORAGE("PARTIAL_STORAGE", "部分入库", 30),

    HAS_BEEN_STORED("HAS_BEEN_STORED", "已入库", 40),

    FINISHED("FINISHED", "已完成", 60),

    CANCEL("CANCEL", "已取消", 70);

    private String code;

    private String message;

    private Integer value;
}
