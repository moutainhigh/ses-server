package com.redescooter.ses.web.ros.vo.sellsy.enter.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @ClassName:SellsyDocumentShippingEnter
 * @description: 订单参数
 * @author: Alex
 * @Version：1.3
 * @create: 2020/08/26 11:26
 */
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class SellsyDocumentShippingEnter {

    //包裹数量 默认为0
    private int nbParcels;
    
    //总重量  默认为0
    private float weight;
    
    //重量单位 'g', 'kg
    private String weightUnit;
    
    //券
    private float volume;
    
    //订单号
    private String trackingNumber;
    
    //查询网页地址
    private String trackingUrl;
    
    //预计交货日期
    private Timestamp date;
}
