package com.redescooter.ses.mobile.rps.vo.assembly;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;

/**
 * @ClassName:QueryProductCodeResult
 * @description: QueryProductCodeResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/04/14 11:23
 */
@ApiModel(value = "查询产品条码打印结果", description = "查询产品条码打印结果")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class QueryProductCodeResult extends GeneralResult {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "打印条码结果")
    private Boolean printCodeResult;
}
