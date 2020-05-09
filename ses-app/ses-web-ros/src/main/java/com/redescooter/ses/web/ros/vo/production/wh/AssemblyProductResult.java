package com.redescooter.ses.web.ros.vo.production.wh;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;

/**
 * @ClassName:AssemblyProductRusult
 * @description: AssemblyProductRusult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/04/03 15:23
 */
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "可组装产品出参列表", description = "可组装产品出参列表")
public class AssemblyProductResult extends GeneralResult {
    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "产品名字")
    private String productName;

    @ApiModelProperty(value = "数量")
    private Integer qty;
}
