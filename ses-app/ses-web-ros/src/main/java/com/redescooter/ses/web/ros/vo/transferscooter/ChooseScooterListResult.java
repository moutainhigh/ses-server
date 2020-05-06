package com.redescooter.ses.web.ros.vo.transferscooter;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.web.ros.vo.customer.ChooseScooterResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

import io.swagger.annotations.*;

/**
 * @ClassName:ChooseScooterListResult
 * @description: ChooseScooterListResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/05/06 18:57
 */
@ApiModel(value = "选择车辆反参", description = "选择车辆反参")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class ChooseScooterListResult extends GeneralResult {

    @ApiModelProperty(value = "应分配车辆数量")
    private int allocateScooterQty;

    @ApiModelProperty(value = "库存列表数据")
    private List<ChooseScooterResult> chooseScooterResultList;
}
