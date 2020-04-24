package com.redescooter.ses.web.ros.vo.customer;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

import io.swagger.annotations.*;

/**
 * @ClassName:TransferScooterEnter
 * @description: TransferScooterEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/04/24 16:46
 */
@ApiModel(value = "车辆分配接口", description = "车辆分配接口")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class TransferScooterEnter extends GeneralEnter {

    @ApiModelProperty(value = "客户Id")
    private Long id;

    @ApiModelProperty(value = "客户库存条目Id")
    private List<Long> stockItemId;
}
