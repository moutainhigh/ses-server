package com.redescooter.ses.web.ros.vo.sellsy.result.briefcases;

import lombok.*;

@Data // 生成getter,setter等函数
@AllArgsConstructor // 生成全参数构造函数
@NoArgsConstructor // 生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class SellsyBriefcasesFolderResult {
    private String id;

    private String corpid;

    private String ownertype;

    private String ownerid;

    private String parentid;

    private String system;

    private String name;

    private String description;

    private String created;
}
