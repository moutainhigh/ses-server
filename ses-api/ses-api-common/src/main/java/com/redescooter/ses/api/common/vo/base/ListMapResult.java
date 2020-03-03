package com.redescooter.ses.api.common.vo.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.*;

/**
 * @ClassName MapResult
 * @Author Jerry
 * @date 2020/02/27 10:39
 * @Description:
 */
@ApiModel(value = "数组Map公共结果集", description = "数组Map公共结果集")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class ListMapResult extends GeneralResult {

    @ApiModelProperty(value = "MAP值")
    List<Map<String, Object>> mapList = new ArrayList<>();
}

