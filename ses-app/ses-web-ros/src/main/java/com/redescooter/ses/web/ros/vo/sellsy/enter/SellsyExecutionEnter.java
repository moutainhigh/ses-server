package com.redescooter.ses.web.ros.vo.sellsy.enter;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
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
    public String method;
    
    @ApiModelProperty(value = "参数")
    private Object params;
    
    @ApiModelProperty(value = "sellsy 方法类型")
    private String SellsyMethodType;
}
