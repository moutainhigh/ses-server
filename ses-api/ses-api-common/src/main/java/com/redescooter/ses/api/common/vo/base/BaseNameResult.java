package com.redescooter.ses.api.common.vo.base;

import io.swagger.annotations.ApiModel;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "通用下拉查询", description = "通用下拉查询")
public class BaseNameResult {
    private Long id;

    private String name;
}
