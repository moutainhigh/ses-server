package com.redescooter.ses.web.ros.vo.production.assembly;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;

/**
 * @ClassName:SaveAssemblyEnter
 * @description: SaveAssemblyEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/30 13:07
 */
@ApiModel(value = "组装单保存", description = "组装单保存")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class SaveAssemblyEnter extends GeneralEnter {

    @ApiModelProperty(value = "收货人Id")
    private Long consigneeId;

    @ApiModelProperty(value = "工厂Id")
    private Long factoryId;

    @ApiModelProperty(value = "组装产品")
    private String productList;
}
