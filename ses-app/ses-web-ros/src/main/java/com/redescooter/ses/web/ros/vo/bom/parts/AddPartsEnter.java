package com.redescooter.ses.web.ros.vo.bom.parts;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.exception.ValidationExceptionBaseCode;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.ros.exception.ValidationExceptionCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @ClassName AddPartsEnter
 * @Author Jerry
 * @date 2020/02/26 20:37
 * @Description:
 */
@ApiModel(value = "新增部品入参", description = "新增部品入参")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class AddPartsEnter extends GeneralEnter {

    @ApiModelProperty(value = "主键", required = true)
    @NotNull(code = ValidationExceptionBaseCode.ID_IS_EMPTY, message = "id 为空")
    private Long id;

    @ApiModelProperty(value = "部品号",required = true)
    @NotNull(code = ValidationExceptionCode.PARTS_NUM_IS_EMPTY, message = "部品号 为空")
    private String partsNumber;
}
