package com.redescooter.ses.web.ros.vo.bom.combination;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.exception.ValidationExceptionBaseCode;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName:DeleteScooterPartEnter
 * @description: DeleteScooterPartEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/02/25 13:21
 */
@ApiModel(value = "整车部件删除", description = "整车部件删除")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class DeletePartEnter extends GeneralEnter {
    @ApiModelProperty(value = "Id",required = true)
    @NotNull(code = ValidationExceptionBaseCode.ID_IS_EMPTY,message = "id 为空")
    private Long id;

    @ApiModelProperty(value = "条目Id",required = true)
    @NotNull(code = ValidationExceptionBaseCode.ID_IS_EMPTY,message = "id 为空")
    private List<Long> ids;
}
