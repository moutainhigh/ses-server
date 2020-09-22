package com.redescooter.ses.web.ros.vo.sellsy.enter.client;

import com.redescooter.ses.web.ros.vo.sellsy.enter.SellsyCommonPaginationEnter;
import io.swagger.annotations.ApiModel;
import lombok.*;

@ApiModel(value = "客户列表", description = "客户列表")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class SellsyClientListEnter {

    //查询
    private SellsyClientListSearchEnter search;

    //分页
    private SellsyCommonPaginationEnter pagination;
}
