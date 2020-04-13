package com.redescooter.ses.web.ros.vo.production.wh;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @ClassName:TobeStoredResult
 * @description: TobeStoredResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/04/03 14:55
 */
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "待入库列表出参列表", description = "待入库列表出参列表")
public class TobeStoredResult extends GeneralResult {

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

    @ApiModelProperty(value = "数量")
    private Integer qty;

}
