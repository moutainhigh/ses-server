package com.redescooter.ses.web.ros.vo.sellsy.enter.document;

import com.redescooter.ses.web.ros.enums.sellsy.SellsyBooleanEnums;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

/**
 * @ClassName:SellsyPaydateAttributesEnter
 * @description: 设置付款期限
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
    private int xdays;
    
    //月底迟交发票
    private SellsyBooleanEnums endmonth;
    
    //结算细节 Yes, if the settlement period has selected the syscode 'scaled'	 由账户默认值
    private Timestamp scaledDetails;
    
    //Date on which the invoice must pass late 发票推迟日期
    private Timestamp custom;
    
    //时间期限
    private List<SellsyDeadlinesEnter> deadlines;
}
