package com.redescooter.ses.web.ros.vo.website;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.exception.ValidationExceptionCode;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @ClassNameGetEailEnter
 * @Description
 * @Author Joan
 * @Date2020/5/20 16:55
 * @Version V1.0
 **/
@ApiModel(value = "Mail subscription", description = "Mail subscription")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class StorageEamilEnter extends GeneralEnter {

    @ApiModelProperty(value = "id", required = true)
    @NotNull(code = ValidationExceptionCode.ID_IS_EMPTY, message = "主键不能为空")
    private Long id;

    @ApiModelProperty(value = "email")
    private String email;
}
