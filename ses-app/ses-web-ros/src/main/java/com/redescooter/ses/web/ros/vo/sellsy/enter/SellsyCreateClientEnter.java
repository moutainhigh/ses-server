package com.redescooter.ses.web.ros.vo.sellsy.enter;

import com.redescooter.ses.web.ros.enums.sellsy.SellsyBooleanEnums;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

import io.swagger.annotations.*;

/**
 * @ClassName:SellsyCreateClientEnter
 * @description: SellsyCreateClientEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/08/24 18:55
 */
@ApiModel(value = "客户创建入参", description = "")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class SellsyCreateClientEnter {
    
    @ApiModelProperty(value = "")
    private String name;
    
    @ApiModelProperty(value = "")
    private String ident;
    
    @ApiModelProperty(value = "")
    private String type;
    
    @ApiModelProperty(value = "")
    private String email;
    
    @ApiModelProperty(value = "")
    private String tel;
    
    @ApiModelProperty(value = "")
    private String fax;
    
    @ApiModelProperty(value = "")
    private String mobile;
    
    @ApiModelProperty(value = "")
    private Timestamp joinDate;
    
    @ApiModelProperty(value = "")
    private String web;
    
    @ApiModelProperty(value = "")
    private String siret;
    
    @ApiModelProperty(value = "")
    private String siren;
    
    @ApiModelProperty(value = "")
    private String vat;
    
    @ApiModelProperty(value = "")
    private String rcs;
    
    @ApiModelProperty(value = "")
    private String apenaf;
    
    @ApiModelProperty(value = "")
    private String capital;
    
    @ApiModelProperty(value = "")
    private String tags;
    
    @ApiModelProperty(value = "")
    private String stickyNote;
    
    @ApiModelProperty(value = "")
    private String rateCategory;
    
    @ApiModelProperty(value = "")
    private SellsyBooleanEnums massmailingUnsubscribed;
    
    @ApiModelProperty(value = "")
    private SellsyBooleanEnums massmailingUnsubscribedSMS;
    
    @ApiModelProperty(value = "")
    private SellsyBooleanEnums phoningUnsubscribed;
    
    @ApiModelProperty(value = "")
    private SellsyBooleanEnums massmailingUnsubscribedMail;
    
    @ApiModelProperty(value = "")
    private SellsyBooleanEnums massmailingUnsubscribedCustom;
    
    @ApiModelProperty(value = "")
    private String facebook;
    
    @ApiModelProperty(value = "")
    private String twitter;
    
    @ApiModelProperty(value = "")
    private String viadeo;
    
    @ApiModelProperty(value = "")
    private String linkedin;
    
    @ApiModelProperty(value = "")
    private String accountingcode;
    
    @ApiModelProperty(value = "")
    private String auxcode;
    
    @ApiModelProperty(value = "")
    private String accountingpurchasecode;
    
    @ApiModelProperty(value = "")//Contact the parameter is required only if the type of the third is person
    private List<SellsyCreateClientContactEnter> contact;
    
    @ApiModelProperty(value = "")//The address parameter is not mandatory
    private List<SellsyCreateClientAddressEnter> address;
    
}
