package com.redescooter.ses.web.ros.vo.production.wh;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName:AvailableResult
 * @description: AvailableResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/04/03 14:29
 */
@ApiModel(value = "可用列表出参列表", description = "可用列表出参列表")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class AvailableListResult extends GeneralResult {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "部品号")
    private String partN;

    @ApiModelProperty(value = "类型")
    private String type;

    @ApiModelProperty(value = "名字")
    private String enName;

    @ApiModelProperty(value = "名字")
    private String cnName;

    @ApiModelProperty(value = "库存")
    private Integer stocks;

    @ApiModelProperty(value = "批次号")
    private List<String> batchNList;

}
