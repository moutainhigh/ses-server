package com.redescooter.ses.mobile.rps.vo.assembly;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;

/**
 * @ClassName:PrintCodeEnter
 * @description: PrintCodeEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/04/14 10:03
 */
@ApiModel(value = "打印条码入参", description = "打印条码入参")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class PrintCodeEnter extends GeneralEnter {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "打印结果")
    private Boolean result;
}
