package com.redescooter.ses.api.common.vo.count;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @ClassName:OrderCountResult
 * @description: OrderCountResult
 * @author: Aleks
 * @Version：1.3
 * @create: 2020/12/16 17:00
 */
@Data
public class OrderCountResult extends GeneralResult {

    @ApiModelProperty("时间")
    private String dateTime;

    @ApiModelProperty("数量")
    private Integer totalCount = 0;


}
