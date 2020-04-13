package com.redescooter.ses.web.ros.vo.production.wh;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @ClassName:AvailableListBatchNResult
 * @description: AvailableListBatchNResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/04/07 14:55
 */
@ApiModel(value = "批次号列表", description = "批次号列表")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class AvailableListBatchNResult extends GeneralResult {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "批次号")
    private String batchN;

    @ApiModelProperty(value = "数量")
    private Integer qty;
}
