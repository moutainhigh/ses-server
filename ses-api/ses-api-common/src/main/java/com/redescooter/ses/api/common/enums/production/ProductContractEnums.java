package com.redescooter.ses.api.common.enums.production;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName:ProductContractEnums
 * @description: ProductContractEnums
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/23 19:14
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ProductContractEnums {

    REDE("REDE", "REDE", "1"),
    //规则 “PN”+“年份”+“日期”+“3位数递增”。例如：PN202002020014
    MATERIALQCBATCHNO("PN","来料质检前缀","2"),
    //产品序列号 生成规则 EG：VXS            DW       EE0    X     X   1        XXXXXX
    //                    REDE公司代码  type型号 版本号 随机数 年份 工厂代码  sn（0-99999累加）
    SCOOTER_SERIAL_NUMBER("VXS","整车序列号","3"),


    ;

    private String code;

    private String message;

    private String value;
}
