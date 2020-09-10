package com.redescooter.ses.web.ros.vo.sellsy.enter.document;

import com.redescooter.ses.web.ros.enums.sellsy.SellsyCurrencySymbolLocationEnms;
import lombok.*;

/**
 * @ClassName:SellsyNumFormatEnter
 * @description: SellsyNumFormatEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/08/26 11:43
 */
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class SellsyNumFormatEnter {

    //货币Id 无默认值
    private int currencyid;

    // enum('left', 'right', 'both', 'none') 货币符号位置 默认right
    private SellsyCurrencySymbolLocationEnms currencypos = SellsyCurrencySymbolLocationEnms.right;

    //十进制分隔符(小数点符号) num(',', '.') 默认 。
    private String decimals = ",";

    //enum(' ', 'right') 数字组分隔符 默认 	' '
    private String thousands = " ";

    // 小数点位置；小数位；小数位数 无默认值 主要指保留几位小数
    private String precision;
}
