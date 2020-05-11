package com.redescooter.ses.mobile.rps.vo.materialqc;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

import io.swagger.annotations.*;

/**
 * @ClassName:ReturnedCompletedEnter
 * @description: ReturnedCompletedEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/05/11 11:51
 */
@ApiModel(value = "", description = "")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class ReturnedCompletedEnter extends GeneralEnter {

    @ApiModelProperty(value = "主订单Id")
    private Long purchasId;

    @ApiModelProperty(value = "子订单Ids")
    private List<Long> ids;
}
