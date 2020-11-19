package com.redescooter.ses.web.ros.vo.sellsy.result.account;

import lombok.*;

@Data // 生成getter,setter等函数
@AllArgsConstructor // 生成全参数构造函数
@NoArgsConstructor // 生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class SellsyAccountAddressResult {
    private String id;

    private String corpid;

    private String status;

    private String rank;

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

    private String isMain;

    private String isMainDeliv;

    private String countryname;

    private String toHTML;
}