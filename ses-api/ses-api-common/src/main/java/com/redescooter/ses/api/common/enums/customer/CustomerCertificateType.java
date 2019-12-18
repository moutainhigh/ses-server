package com.redescooter.ses.api.common.enums.customer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName:CustomerCertificateType
 * @description: CustomerCertificateType
 * @author: Alex
 * @Version：1.0
 * @create: 2019/12/18 15:39
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum CustomerCertificateType {

    ID_CARD("ID_CARD","身份证号"),
    DRIVER_LICENSE("DRIVER_LICENSE","驾照号"),
    PASSPORT("PASSPORT","护照");


    private String code;

    private String message;
}
