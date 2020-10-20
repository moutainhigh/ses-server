package com.redescooter.ses.web.ros.vo.production.purchasing;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.exception.ValidationExceptionBaseCode;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @ClassName:PruchasingDetailProductEnter
 * @description: PruchasingDetailProductEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/30 18:15
 */
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class PruchasingDetailProductEnter extends GeneralEnter {

    @ApiModelProperty(value = "id 采购单id")
    @NotNull(code = ValidationExceptionBaseCode.ID_IS_EMPTY, message = "Id 不为空")
    private Long id;

    @ApiModelProperty(value = "类型")
    private String type;

    @ApiModelProperty(value = "供应商Id")
    private Long supplierId;

    @ApiModelProperty(value = "关键字")
    private String keyword;
}
