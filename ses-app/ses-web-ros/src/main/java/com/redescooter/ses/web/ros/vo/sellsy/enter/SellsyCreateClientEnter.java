package com.redescooter.ses.web.ros.vo.sellsy.enter;

import com.redescooter.ses.web.ros.enums.sellsy.SellsyBooleanEnums;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @ClassName:SellsyCreateClientEnter
 * @description: SellsyCreateClientEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/08/24 18:55
 */
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class SellsyCreateClientEnter {

    private String name;
    
    private String ident;
    
    private String type;
    
    private String email;
    
    private String tel;
    
    private String fax;
    
    private String mobile;
    
    private Timestamp joinDate;
    
    private String web;
    
    private String siret;
    
    private String siren;
    
    private String vat;
    
    private String rcs;
    
    private String apenaf;
    
    private String capital;
    
    private String tags;
    
    private String stickyNote;
    
    private String rateCategory;
    
    private SellsyBooleanEnums massmailingUnsubscribed;
    
    private SellsyBooleanEnums massmailingUnsubscribedSMS;
    
    private SellsyBooleanEnums phoningUnsubscribed;
    
    private  SellsyBooleanEnums massmailingUnsubscribedMail;
    
    private SellsyBooleanEnums  massmailingUnsubscribedCustom;
    
    private String facebook;
    
    private String twitter;
    
    private String viadeo;
    
    private String linkedin;
    
    private String accountingcode;
    
    private String auxcode;
    
    private String accountingpurchasecode;
    
    //Contact the parameter is required only if the type of the third is person
    
}
