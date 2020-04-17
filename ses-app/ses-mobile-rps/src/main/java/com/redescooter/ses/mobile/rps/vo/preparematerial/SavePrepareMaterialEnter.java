package com.redescooter.ses.mobile.rps.vo.preparematerial;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @ClassName:SavePrepareMaterialEnter
 * @description: SavePrepareMaterialEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/04/13 19:14
 */
@ApiModel(value = "保存备料入参", description = "保存备料入参")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class SavePrepareMaterialEnter extends GeneralEnter {

    @ApiModelProperty(value = "备料部件列表")
    private String partListJson;
}
