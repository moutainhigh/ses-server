package com.redescooter.ses.web.ros.vo.sellsy.result.account;

import lombok.*;

@Data // 生成getter,setter等函数
@AllArgsConstructor // 生成全参数构造函数
@NoArgsConstructor // 生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class SellsyUnitResult {
    private String id;

    private String corpid;

    private String list;

    private String status;

    private String isEnabled;

    private String isWriteabled;

    private String syscode;

    private String rank;

    private String value;

    private String more;

}
