package com.redescooter.ses.mobile.rps.vo.assembly;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;

/**
 * @ClassName:PrintCodeResult
 * @description: PrintCodeResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/04/28 11:03
 */
@ApiModel(value = "条码打印结果", description = "条码打印结果")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class PrintCodeResult extends GeneralResult {

    @ApiModelProperty(value = "条码打印结果")
    private Boolean printCodeResult;
}
