package com.redescooter.ses.web.ros.vo.sellsy.enter.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @ClassName:SellsyDeadlinesEnter
 * @description: SellsyDeadlinesEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/08/26 11:19
 */
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class SellsyDeadlinesEnter {

    //截止日期
    private Timestamp date;

    //金额
     private float  amount;
}
