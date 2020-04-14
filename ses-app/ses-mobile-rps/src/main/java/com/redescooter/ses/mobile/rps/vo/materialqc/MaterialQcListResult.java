package com.redescooter.ses.mobile.rps.vo.materialqc;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

import io.swagger.annotations.*;

/**
 * @ClassName:MaterialQcListResult
 * @description: MaterialQcListResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/04/13 16:18
 */
@ApiModel(value = "来料质检列表", description = "来料质检列表")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class MaterialQcListResult extends GeneralResult {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "订单创建时间")
    private Date createdTime;

    @ApiModelProperty(value = "订单编号")
    private String purchasN;

    @ApiModelProperty(value = "待质检总数")
    private int laveWaitQcTotal;
}
