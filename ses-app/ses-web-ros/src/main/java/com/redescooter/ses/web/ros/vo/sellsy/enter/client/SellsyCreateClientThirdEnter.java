package com.redescooter.ses.web.ros.vo.sellsy.enter.client;

import com.redescooter.ses.web.ros.enums.sellsy.SellsyBooleanEnums;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.sql.Timestamp;

@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class SellsyCreateClientThirdEnter {
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
}
