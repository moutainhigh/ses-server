package com.redescooter.ses.web.ros.vo.sellsy.result;

import io.swagger.annotations.ApiModel;
import lombok.*;

@ApiModel(value = "Sellsy 通用反参", description = "\"Sellsy 通用反参")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class SellsyIdResut {

    private Integer id;
}
