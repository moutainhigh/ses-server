package com.redescooter.ses.web.ros.vo.sellsy.enter.document;

import lombok.*;

@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class SellsyDocumentOrderListEnter {
    //排序 ASC  or DESC
    private String direction;

    // 文档日期 doc_ident or doc_thirdname or doc_displayedDate or doc_totalAmountTaxesFree
    private String order;

}