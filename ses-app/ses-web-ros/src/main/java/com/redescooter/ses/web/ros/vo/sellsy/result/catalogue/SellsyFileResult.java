package com.redescooter.ses.web.ros.vo.sellsy.result.catalogue;

import lombok.*;

@Data // 生成getter,setter等函数
@AllArgsConstructor // 生成全参数构造函数
@NoArgsConstructor // 生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class SellsyFileResult {

    private String public_path;
}
