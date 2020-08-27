package com.redescooter.ses.web.ros.vo.sellsy.result.account;

import lombok.*;

@Data // 生成getter,setter等函数
@AllArgsConstructor // 生成全参数构造函数
@NoArgsConstructor // 生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class SellsyCurrencyResult {
    private String name;

    private String longname;

    private String symbol;

    private String rank;

    private String mercacode;

    private String paypalcode;

    private String enabled;

    private String id;

    private String selected;
}
