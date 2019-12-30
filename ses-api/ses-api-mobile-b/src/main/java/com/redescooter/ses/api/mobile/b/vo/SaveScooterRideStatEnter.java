package com.redescooter.ses.api.mobile.b.vo;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @ClassName:SaveScooterRideStatEnter
 * @description: SaveScooterRideStatEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/30 16:56
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class SaveScooterRideStatEnter extends GeneralEnter {
    private Long id;
}
