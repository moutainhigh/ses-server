package com.redescooter.ses.web.ros.vo.bom.parts;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName:PartUnbindEnter
 * @description: PartUnbindEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/04/08 17:32
 */
@ApiModel(value = "部件解绑商品", description = "部件解绑商品")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class PartUnbindEnter extends GeneralEnter {

    @ApiModelProperty(value = "部件Id")
    private Long id;

    @ApiModelProperty(value = "商品Id")
    private List<Long> productIds;
}
