package com.redescooter.ses.web.ros.vo.wms.cn;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;

/**
 * @ClassName:WhOutDetailProductPartListResult
 * @description: WhOutDetailProductPartListResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/07/20 11:49
 */
@ApiModel(value = "详情产品列表", description = "详情产品列表")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class WhOutDetailProductPartListResult extends GeneralResult {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "产品编号")
    private String productN;

    @ApiModelProperty(value = "类型")
    private String productType;

    @ApiModelProperty(value = "名称")
    private String enName;

    @ApiModelProperty(value = "名称")
    private String cnName;

    @ApiModelProperty(value = "数量")
    private int qty;
}
