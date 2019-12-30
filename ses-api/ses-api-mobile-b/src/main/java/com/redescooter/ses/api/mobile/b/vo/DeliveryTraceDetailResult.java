package com.redescooter.ses.api.mobile.b.vo;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @ClassName:DeliveryTraceDetailResult
 * @description: DeliveryTraceDetailResult
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/29 16:16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
public class DeliveryTraceDetailResult extends GeneralResult {

    private Long id;

}
