package com.redescooter.ses.api.common.enums.customer;

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

    ID_CARD("ID_CARD", "身份证号", "1"),
    DRIVER_LICENSE("DRIVER_LICENSE", "驾驶证", "2"),
    PASSPORT("PASSPORT", "护照", "3");

    private String code;

    private String message;

    private String value;

    public static CustomerCertificateTypeEnum getCustomerCertificateTypeEnumByCode(String value) {
        for (CustomerCertificateTypeEnum item : CustomerCertificateTypeEnum.values()) {
            if (item.getCode().equals(value)) {
                return item;
            }
        }
        return null;
    }

}
