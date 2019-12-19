package com.redescooter.ses.api.common.enums.ros.customer;

import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName:CustomerCertificateType
 * @description: CustomerCertificateTypeEnum
 * @author: Alex
 * @Version：1.0
 * @create: 2019/12/18 15:39
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum CustomerCertificateTypeEnum {

    ID_CARD("ID_CARD", "身份证号", 1),
    DRIVER_LICENSE("DRIVER_LICENSE", "驾照号", 2),
    PASSPORT("PASSPORT", "护照", 3);

    private String code;

    private String message;

    private Integer value;

}
