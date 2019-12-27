package com.redescooter.ses.api.scooter.vo;

import com.redescooter.ses.api.common.vo.base.PageEnter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @ClassName:ScooterRecordListEnter
 * @description: ScooterRecordListEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/27 10:30
 */
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
@Data
public class ScooterRecordListEnter extends PageEnter {

    private Long scooterId;
}
