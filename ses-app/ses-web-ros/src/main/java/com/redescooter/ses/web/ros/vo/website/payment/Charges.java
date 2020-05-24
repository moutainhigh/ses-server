package com.redescooter.ses.web.ros.vo.website.payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@lombok.Data // 生成getter,setter等函数
@AllArgsConstructor // 生成全参数构造函数
@NoArgsConstructor // 生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class Charges {

    private String object;
    private List<Data> data;
    private boolean hasMore;
    private int totalCount;
    private String url;

}