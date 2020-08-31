package com.redescooter.ses.web.ros.vo.sellsy.result.account;

import lombok.*;

@Data // 生成getter,setter等函数
@AllArgsConstructor // 生成全参数构造函数
@NoArgsConstructor // 生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class SellsyCorpInfoResult {
    private String linkedtype;

    private String linkedid;

    private String prefsid;

    private String accountingPrefsId;

    private String logo;

    private String name;

    private String email;

    private String web;

    private String tel;

    private String mobile;

    private String fax;

    private String siret;

    private String vat;

    private String apenaf;

    private String rcs;

    private String type;

    private String capital;

    private String mainaddressid;

    private String id;
}
