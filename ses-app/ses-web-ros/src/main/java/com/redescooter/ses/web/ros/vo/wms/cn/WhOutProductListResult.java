package com.redescooter.ses.web.ros.vo.wms.cn;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;

/**
 * @ClassName:WhOutProductListResult
 * @description: WhOutProductListResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/07/20 14:19
 */
@ApiModel(value = "", description = "")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class WhOutProductListResult extends GeneralResult {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "产品编号")
    private String productN;

    @ApiModelProperty(value = "名称")
    private String enName;

    @ApiModelProperty(value = "名称")
    private String frName;

    @ApiModelProperty(value = "名称")
    private String cnName;

    @ApiModelProperty(value = "类型")
    private String type;

    @ApiModelProperty(value = "库存")
    private Integer stocks;
}
