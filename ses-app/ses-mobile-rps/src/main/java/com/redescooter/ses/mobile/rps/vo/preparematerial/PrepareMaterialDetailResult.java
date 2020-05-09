package com.redescooter.ses.mobile.rps.vo.preparematerial;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

/**
 * @ClassName:PrepareMaterialDetailResult
 * @description: PrepareMaterialDetailResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/04/13 19:08
 */
@ApiModel(value = "待备料详情出参", description = "待备料详情出参")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class PrepareMaterialDetailResult extends GeneralResult {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "子订单Id")
    private Long childOrderId;

    @ApiModelProperty(value = "来源类型")
    private String sourceType;

    @ApiModelProperty(value = "部件Id")
    private Long partId;

    @ApiModelProperty(value = "部件编号")
    private String partN;

    @ApiModelProperty(value = "部件名称")
    private String partCnName;

    @ApiModelProperty(value = "有无Id")
    private Boolean idClass;

    @ApiModelProperty(value = "待备料数量")
    private int preparationWaitQty;

    @ApiModelProperty(value = "可用序列号列表")
    private List<String> serialNumList;
}
