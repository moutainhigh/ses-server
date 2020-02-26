package com.redescooter.ses.web.ros.vo.bom.parts;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName HistoryPartsResult
 * @Author Jerry
 * @date 2020/02/26 23:09
 * @Description:
 */
@ApiModel(value = "部品历史记录", description = "部品历史记录")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class HistoryPartsResult extends GeneralResult {

    @ApiModelProperty(value = "部品号")
    List<String> list = new ArrayList<>();
}
