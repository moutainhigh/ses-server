package com.redescooter.ses.api.common.enums.restproductionorder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassNameAllocateOrderStatusEnum
 * @Description 采购单状态枚举
 * @Author Aleks
 * @Date2020/10/26 16:16
 * @Version V1.0
 **/
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum PurchaseOrderStatusEnum {


    DRAFT("DRAFT", "草稿", 0),

    WAIT_STOCK("WAIT_STOCK", "待备货", 10),

    STOCKING("STOCKING", "备货中", 20),

    WAIT_DELIVER("WAIT_DELIVER", "待发货", 30),

    WAIT_SIGN("WAIT_SIGN", "待签收", 40),

    SIGNED("SIGNED", "已签收", 50),

    FINISHED("FINISHED", "已完成", 60),

    CANCEL("CANCEL", "已取消", 70);


    private String code;

    private String message;

    private Integer value;



}
