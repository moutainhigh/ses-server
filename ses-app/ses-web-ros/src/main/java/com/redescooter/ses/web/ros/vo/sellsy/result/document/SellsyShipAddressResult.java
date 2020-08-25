package com.redescooter.ses.web.ros.vo.sellsy.result.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @ClassName:SellsyShipAddressResult
 * @description: SellsyShipAddressResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/08/25 16:12
 */
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class SellsyShipAddressResult {
    
    private String id;
    
    private String name;
    
    private String part1;
    
    private String part2;
    
    private String part3;
    
    private String part4;
    
    private String zip;
    
    private String town;
    
    private String state;
    
    private String countrycode;
    
    private String originalid;
    
    private String lat;
    
    private String lng;
    
}
