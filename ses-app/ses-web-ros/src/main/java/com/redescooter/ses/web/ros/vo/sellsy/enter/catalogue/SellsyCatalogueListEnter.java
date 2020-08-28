package com.redescooter.ses.web.ros.vo.sellsy.enter.catalogue;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.web.ros.enums.sellsy.SellsyCatalogueEnums;
import com.redescooter.ses.web.ros.exception.ThirdValidationExceptionCode;

import lombok.*;

@Data // 生成getter,setter等函数
@AllArgsConstructor // 生成全参数构造函数
@NoArgsConstructor // 生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class SellsyCatalogueListEnter {

    @NotNull(code = ThirdValidationExceptionCode.SELLSY_CATALOGUE_TYPE_IS_EMPTY, message = "产品类型为空")
    private SellsyCatalogueEnums type;
}
