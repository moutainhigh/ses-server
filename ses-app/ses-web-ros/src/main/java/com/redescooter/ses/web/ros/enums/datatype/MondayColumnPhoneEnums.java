package com.redescooter.ses.web.ros.enums.datatype;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName:MondayColumnPhoneEnums
 * @description: MondayColumnPhoneEnums
 * @author: Alex
 * @Version：1.3
 * @create: 2020/07/24 16:20
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum MondayColumnPhoneEnums {
    /**
     * {
     *   "phone": "11231234567",
     *   "countryShortName": "US"
     * }
     */
    phone("phone","countryShortName");

    private String phoneTel;

    private String countryShortName;
}
