package com.redescooter.ses.web.ros.vo.sellsy.enter.catalogue;

import com.redescooter.ses.web.ros.enums.sellsy.SellsyBooleanEnums;
import lombok.*;

@Data // 生成getter,setter等函数
@AllArgsConstructor // 生成全参数构造函数
@NoArgsConstructor // 生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class SellsyCatalogueListSearchEnter {
    private String name;

    private String tags;

    private SellsyBooleanEnums inPos;

    // 费率类别的 ID。退回的金额将是费率类别的金额
    private Integer rateCategory;

    //只是拒收或不拒收回收产品
    private SellsyBooleanEnums useDeclination;

    //检索变量或父产品。 useDeclination 参数必须是 y 才能使用它
    private SellsyBooleanEnums combineDecli;

    //允许您检索活动(或非)产品
    private SellsyBooleanEnums actif;

    //允许您检索链接到类别的项
    private String categoryid;

    //允许您检索可用的项目(在库存 + 那些不使用库存)或出库存
    private SellsyBooleanEnums stockAvailable;

    //允许您检索项目使用股票
    private SellsyBooleanEnums stockEnabled;

    //允许您检索库存警报中的项目(必须启用库存附加组件)
    private SellsyBooleanEnums alertstock;
}
