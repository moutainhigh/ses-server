package com.redescooter.ses.web.ros.vo.customer;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;

/**
 * @ClassName:TransferScooterListEnter
 * @description: TransferScooterListEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/04/24 18:26
 */
@ApiModel(value = "", description = "")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class TransferScooterListEnter extends GeneralEnter {

    @ApiModelProperty(value = "库存条目Id")
    private Long id;

    @ApiModelProperty(value = "车牌号")
    private String numberPlate;
}
