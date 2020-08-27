package com.redescooter.ses.web.ros.vo.sellsy.result.account;

import lombok.*;

@Data // 生成getter,setter等函数
@AllArgsConstructor // 生成全参数构造函数
@NoArgsConstructor // 生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class SellsyLayoutResult {
    private String corpid;

    private String ownerid;

    private String status;

    private String isdefault;

    private String name;

    private String headerMode;

    private String footerMode;

    private String logoMode;

    private String useBg;

    private String bgMode;

    private String bgFile;

    private String bgCustomFile;

    private String hideTopPart;

    private String hideBottomSeparator;

    private String documentIdentOnLeft;

    private String hideFill;

    private String colorFill;

    private String hideDepositRecap;

    private String colorText;

    private String colorHeaderRow;

    private String colorNotes;

    private String colorDiscreet;

    private String colorHeaderFooter;

    private String font;

    private String fontsize;

    private String language;

    private String langid;

    private String timeformat;

    private String txts;

    private String customName;

    private String topMargin;

    private String headerAlign;

    private String documentAddressOnLeft;

    private String documentFormat;

    private String pageNumber;

    private String alignDocNumber;

    private String numericDataAlign;

    private String id;
}
