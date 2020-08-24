package com.redescooter.ses.web.ros.vo.sellsy.enter;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.exception.ValidationException;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.ros.exception.ValidationExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;

/**
 * @ClassName:SellsyExecutionEnter
 * @description: SellsyExecutionEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/08/19 17:10
 */
@ApiModel(value = "sellsy执行器入参", description = "sellsy执行器入参")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class SellsyExecutionEnter extends GeneralEnter {
    
    
    @ApiModelProperty(value = "sellsy 方法")
    @NotNull(code = ValidationExceptionCode.SELLSY_METHOD_IS_EMPTY,message = "Sellsy method is empty.")
    public String method;
    
    @ApiModelProperty(value = "参数")
    private Object params;
    
    @ApiModelProperty(value = "sellsy 方法类型")
    @NotNull(code = ValidationExceptionCode.SELLSY_METHOD_TYPE_IS_EMPTY,message = "Sellsy method type is empty.")
    private String SellsyMethodType;
}