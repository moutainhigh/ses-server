package com.redescooter.ses.web.ros.vo.sellsy.enter.document;

import com.redescooter.ses.web.ros.enums.sellsy.SellsyBooleanEnums;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @ClassName:SellsyPaydateAttributesEnter
 * @description: SellsyPaydateAttributesEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/08/26 10:30
 */
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class SellsyPaydateAttributesEnter {

    private int id;
    
    //期望天数之前通过的发票迟交 有账户默认值
    private int paydate_xdays;
    
    //月底迟交发票
    private SellsyBooleanEnums paydate_endmonth;
    
    //结算细节 Yes, if the settlement period has selected the syscode 'scaled'	 由账户默认值
    private Timestamp paydate_scaledDetails;

    private Timestamp scaledDetails;
    
    private timestamp custom;
    
            'custom'        => {{paydate_custom}}
            'scaledDetails' => {{paydate_scaledDetails}},
            'deadlines' => array(
            array(
            'date'      => {{date}},
            'amount'    => {{amount}},
            ),
    array(
                    'date'      => {{date}},
            'amount'    => {{amount}},
            ),
            )
            ),

    
}
