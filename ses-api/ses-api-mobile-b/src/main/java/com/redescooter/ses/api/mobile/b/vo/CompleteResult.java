package com.redescooter.ses.api.mobile.b.vo;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @ClassName:CompleteResult
 * @description: CompleteResult
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/29 15:26
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@EqualsAndHashCode(callSuper = true)
public class CompleteResult extends GeneralResult {

    private String mileage;

    private String avg;

    private String co2;

    private String money;

}
