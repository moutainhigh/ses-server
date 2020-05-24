package com.redescooter.ses.web.ros.vo.website.payment;

import lombok.Data;
import lombok.*;

import java.util.List;

@Data // 生成getter,setter等函数
@AllArgsConstructor // 生成全参数构造函数
@NoArgsConstructor // 生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class Refunds {

    private String object;
    private List<String> data;
    private boolean hasMore;
    private int totalCount;
    private String url;

}