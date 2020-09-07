package com.redescooter.ses.web.ros.vo.sellsy.result.briefcases;

import lombok.*;

@Data // 生成getter,setter等函数
@AllArgsConstructor // 生成全参数构造函数
@NoArgsConstructor // 生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class SellsyBriefcasesFileResult {
    private String corpid;

    private String ownerid;

    private String ownertype;

    private String id;

    private String name;

    private String size;

    private String created;

    private String lastSyncDropbox;

    private String extension;

    private String directorie;

    private String attachtoestimate;

    private String attachtoinvoice;

    private String attachtodelivery;

    private String attachtoorder;

    private String attachtocreditnote;

    private String attachtoproforma;

    private String attachtopurOrder;

    private String attachtopurDelivery;

    private String attachtopurInvoice;

    private String attachtopurCreditNote;

    private String system;

    private String file_fullname;

    private String public_url;
}
