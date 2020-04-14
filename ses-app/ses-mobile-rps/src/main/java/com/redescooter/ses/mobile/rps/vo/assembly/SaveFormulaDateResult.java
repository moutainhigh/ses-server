package com.redescooter.ses.mobile.rps.vo.assembly;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

import io.swagger.annotations.*;

/**
 * @ClassName:SaveFormulaDateResult
 * @description: SaveFormulaDateResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/04/13 20:18
 */
@ApiModel(value = "保存整车配方入参", description = "保存整车配方入参")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class SaveFormulaDateResult extends GeneralResult {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "产品名称")
    private String productName;

    @ApiModelProperty(value = "产品编号")
    private String productN;

    @ApiModelProperty(value = "序列号")
    private String serialNum;

    @ApiModelProperty(value = "创建时间")
    private Date createdTime;
}
