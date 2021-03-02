package com.redescooter.ses.api.common.enums.restproductionorder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassNameInWhouseOrderEnum
 * @Description 入库单状态
 * @Author Aleks
 * @Date2020/11/11 18:17
 * @Version V1.0
 **/
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum InWhouseOrderStatusEnum {

    DRAFT("DRAFT", "草稿", 1),

    WAIT_INSPECTED("WAIT_INSPECTED", "待质检", 10),

    INSPECTING("INSPECTING", "质检中", 20),

    WAIT_IN_WH("WAIT_IN_WH", "待入库", 25),

    ALREADY_IN_WHOUSE("ALREADY_IN_WHOUSE", "已入库", 30);



    private String code;

    private String message;

    private Integer value;

}
