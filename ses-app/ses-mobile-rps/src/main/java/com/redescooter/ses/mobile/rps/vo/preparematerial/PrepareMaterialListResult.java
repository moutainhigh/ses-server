package com.redescooter.ses.mobile.rps.vo.preparematerial;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

import io.swagger.annotations.*;

/**
 * @ClassName:PrepareMaterialListEnter
 * @description: PrepareMaterialListEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/04/13 19:00
 */
@ApiModel(value = "待备料列表出参", description = "待备料列表出参")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class PrepareMaterialListResult extends GeneralResult {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "调拨单号")
    private String allocatN;

    @ApiModelProperty(value = "调拨单创建时间")
    private Date createdTime;

    @ApiModelProperty(value = "待备料数量")
    private int preparationWaitTotal;

}
