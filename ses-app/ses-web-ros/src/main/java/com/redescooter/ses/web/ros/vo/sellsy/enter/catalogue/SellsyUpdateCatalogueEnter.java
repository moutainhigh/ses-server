package com.redescooter.ses.web.ros.vo.sellsy.enter.catalogue;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.exception.ValidationExceptionCode;
import com.redescooter.ses.web.ros.enums.sellsy.SellsyCatalogueTypeEnums;
import com.redescooter.ses.web.ros.exception.ThirdValidationExceptionCode;

import lombok.*;

@Data // 生成getter,setter等函数
@AllArgsConstructor // 生成全参数构造函数
@NoArgsConstructor // 生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class SellsyUpdateCatalogueEnter {
    //产品类型
    @NotNull(code = ThirdValidationExceptionCode.SELLSY_CATALOGUE_TYPE_IS_EMPTY,message = "产品类型为空")
    private SellsyCatalogueTypeEnums type;

    private SellsyCreateCatalogueTypeEnter item;

    @NotNull(code = ValidationExceptionCode.ID_IS_EMPTY,message = "Id为空")
    private Integer id;
}
