package com.redescooter.ses.mobile.rps.vo.assembly;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;

/**
 * @ClassName:SaveFormulaPartListEnter
 * @description: SaveFormulaPartListEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/04/14 10:00
 */
@ApiModel(value = "保存配方部件入参", description = "保存配方部件入参")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class SaveFormulaPartListEnter extends GeneralEnter {

    @ApiModelProperty(value = "部件 id")
    private Long id;

    @ApiModelProperty(value = "数量")
    private int qty;

    @ApiModelProperty(value = "序列号")
    private String serialN;
}
