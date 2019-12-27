package com.redescooter.ses.api.scooter.vo;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @ClassName:SaveScooterRecord
 * @description: SaveScooterRecord
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/27 10:19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class SaveScooterRecordEnter<T> extends GeneralEnter {

    private String actionType;

    private BigDecimal longitude;

    private BigDecimal latitude;

    private T t;
}
