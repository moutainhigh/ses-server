package com.redescooter.ses.api.mobile.b.vo;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @ClassName:CompleteEnter
 * @description: CompleteEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/29 17:22
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@Data
public class CompleteEnter extends GeneralEnter {

    private Long id;

    private Boolean bluetoothCommunication = Boolean.FALSE;

    private String lat;

    private String lon;

    private String mileage;
}
