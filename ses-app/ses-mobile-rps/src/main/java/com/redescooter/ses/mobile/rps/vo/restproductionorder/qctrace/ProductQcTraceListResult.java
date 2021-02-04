package com.redescooter.ses.mobile.rps.vo.restproductionorder.qctrace;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import lombok.*;

import java.util.List;

import io.swagger.annotations.*;

/**
 * @ClassName:ProductQcTempleteListResult
 * @description: ProductQcTempleteListResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/11/04 18:42 
 */
@ApiModel(value = "产品质检记录", description = "产品质检记录")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
public class ProductQcTraceListResult extends GeneralResult {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "订单序列号绑定表Id")
    private Long orderSerialId;

    @ApiModelProperty(value = "序列号")
    private String serialNum;

    @ApiModelProperty(value = "批次号")
    private String batchNo;

    @ApiModelProperty(value = "质检结果 1 成功 2失败")
    private Integer qcResult;

    @ApiModelProperty(value = "附件")
    private String annex;

    @ApiModelProperty(value = "创建人")
    private Long createdBy;

    @ApiModelProperty(value = "创建时间")
    private Long createdTime;

    @ApiModelProperty(value = "模版质检信息")
    private List<ProductQcTraceInfoResult> productQcTraceInfoResultList;
}
