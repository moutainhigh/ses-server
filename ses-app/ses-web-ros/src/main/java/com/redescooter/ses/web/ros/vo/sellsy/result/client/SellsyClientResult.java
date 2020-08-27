package com.redescooter.ses.web.ros.vo.sellsy.result.client;

import com.redescooter.ses.api.common.vo.base.PageResult;
import com.redescooter.ses.web.ros.vo.sellsy.result.SellsyAvatarGeneralResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @ClassName:SellsyClientResult
 * @description: SellsyClientResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/08/24 17:14
 */
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class SellsyClientResult extends PageResult {
    
    private String thirdid;
    
    private String capital;
    
    private String logo;
    
    private String joindate;
    
    private String auxCode;
    
    private String accountingCode;
    
    private String stickyNote;
    
    private String ident;
    
    private String rateCategory;
    
    private String massmailingUnsubscribed;
    
    private String massmailingUnsubscribedSMS;
    
    private String phoningUnsubscribed;
    
    private String massmailingUnsubscribedMail;
    
    private String massmailingUnsubscribedCustom;
    
    private String lastactivity;
    
    private String ownerid;
    
    private String type;
    
    private String maincontactid;
    
    private String relationType;
    
    private String actif;
    
    private String pic;
    
    private String people_forename;
    
    private String people_name;
    
    private String people_civil;
    
    private String dateTransformProspect;
    
    private String score;
    
    private String mainContactName;
    
    private String name;
    
    private String tel;
    
    private String fax;
    
    private String email;
    
    private String mobile;
    
    private String apenaf;
    
    private String rcs;
    
    private String siret;
    
    private String siren;
    
    private String vat;
    
    private String mainaddressid;
    
    private String maindelivaddressid;
    
    private String web;
    
    private String corpType;
    
    private String addr_name;
    
    private String addr_part1;
    
    private String addr_part2;
    
    private String addr_zip;
    
    private String addr_town;
    
    private String addr_state;
    
    private String addr_lat;
    
    private String addr_lng;
    
    private String addr_countrycode;
    
    private String delivaddr_name;
    
    private String delivaddr_part1;
    
    private String delivaddr_part2;
    
    private String delivaddr_zip;
    
    private String delivaddr_town;
    
    private String delivaddr_state;
    
    private String delivaddr_lat;
    
    private String delivaddr_lng;
    
    private String delivaddr_countrycode;
    
    private String formated_joindate;
    
    private String formated_transformprospectdate;
    
    private String scoreFormatted;
    
    private String scoreClass;
    
    private String corpid;
    
    private SellsyAvatarGeneralResult avatar;
    
    private String lastactivity_formatted;
    
    private String fullName;
    
    private String contactId;
    
    private String contactDetails;
    
    private String formatted_tel;
    
    private String formatted_mobile;
    
    private String formatted_fax;
    
    private String owner;
    
    private String webUrl;
    
    private String accountingCodeFormatted;
    
    private String id;
}
