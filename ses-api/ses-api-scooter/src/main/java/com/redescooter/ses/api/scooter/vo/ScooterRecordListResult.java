package com.redescooter.ses.api.scooter.vo;

import com.redescooter.ses.api.common.vo.base.GeneralResult;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @ClassName:ScooterRecordListResult
 * @description: ScooterRecordListResult
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/27 10:29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class ScooterRecordListResult extends GeneralResult {

    private Long id;

}
