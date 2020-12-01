package com.redescooter.ses.web.ros.vo.restproductionorder.assembly;

import com.redescooter.ses.api.common.annotation.NotEmpty;
import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.exception.ValidationExceptionBaseCode;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.ros.exception.ValidationExceptionCode;
import lombok.*;

import io.swagger.annotations.*;

/**
 * @ClassName:AssemblyOrderDetailEnter
 * @description: AssemblyOrderDetailEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/11/11 14:43 
 */
@ApiModel(value = "组装单详情入参", description = "组装单详情入参")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
public class AssemblyOrderDetailEnter extends GeneralEnter {

    @ApiModelProperty(value = "id")
    @NotNull(code = ValidationExceptionBaseCode.ID_IS_EMPTY,message = "Id为空")
    private Long id;

    @ApiModelProperty(value = "页面类型 1 车辆 2 组合")
    @NotNull(code = ValidationExceptionCode.TYPE_IS_EMPTY,message = "类型为空")
    private Integer classType;
}
