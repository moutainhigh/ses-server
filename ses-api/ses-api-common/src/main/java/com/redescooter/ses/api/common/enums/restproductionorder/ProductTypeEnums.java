package com.redescooter.ses.api.common.enums.restproductionorder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 *  @author: alex
 *  @Date: 2020/10/26 14:28
 *  @version：V ROS 1.8.3
 *  @Description: 适用于 部件生产过程中Table标签筛选
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ProductTypeEnums {
    SCOOTER("SCOOTER", "整车", 1),
    COMBINATION("COMBINATION", "组合", 2),
    PARTS("PARTS", "部件", 3);

    private String code;

    private String message;

    private Integer value;
}
