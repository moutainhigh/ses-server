package com.redescooter.ses.web.ros.vo.sellsy.enter.document;

import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.web.ros.enums.sellsy.SellsyBooleanEnums;
import com.redescooter.ses.web.ros.enums.sellsy.SellsyDocmentTypeEnums;
import com.redescooter.ses.web.ros.exception.ThirdValidationExceptionCode;
import com.redescooter.ses.web.ros.vo.sellsy.enter.SellsyCommonPaginationEnter;
import lombok.*;

/**
 * @ClassName:SellsyDocumentListEnter
 * @description: SellsyDocumentListEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/08/25 14:26
 */
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class SellsyDocumentListEnter {

    //单据类型
    @NotNull(code = ThirdValidationExceptionCode.SELLSY_DOCTYPE_IS_EMPTY, message = "单据类型为空")
    private SellsyDocmentTypeEnums doctype;

    //在清单中包括付款信息(只有发票) yes or no
    private SellsyBooleanEnums includePayments;

    private SellsyDocumentOrderListEnter order;

    private SellsyDocumentSearchListEnter search;

    private SellsyCommonPaginationEnter pagination;
}
