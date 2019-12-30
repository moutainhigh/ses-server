package com.redescooter.ses.api.mobile.b.vo;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @ClassName:StartEnter
 * @description: StartEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/29 15:55
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class StartEnter extends GeneralEnter {

    private Long id;

    private Boolean bluetoothCommunication = Boolean.FALSE;

    private String lat;

    private String lon;
}
