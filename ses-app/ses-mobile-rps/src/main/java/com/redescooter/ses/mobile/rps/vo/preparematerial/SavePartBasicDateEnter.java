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
 * @ClassName:SavePartBasicDateEnter
 * @description: SavePartBasicDateEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/04/15 12:15
 */
@ApiModel(value = "保存备料部件基本数据", description = "保存备料部件基本数据")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class SavePartBasicDateEnter extends GeneralEnter {

    @ApiModelProperty(value = "部件Id")
    private Long partId;

    @ApiModelProperty(value = "序列号")
    private String serialN;
}
