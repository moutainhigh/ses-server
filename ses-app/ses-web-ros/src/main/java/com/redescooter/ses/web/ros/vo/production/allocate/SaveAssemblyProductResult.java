package com.redescooter.ses.web.ros.vo.production.allocate;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;

/**
 * @ClassName:SaveAssemblyProductResult
 * @description: SaveAssemblyProductResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/31 16:20
 */
@ApiModel(value = "新建组装单商品列表", description = "新建组装单商品列表")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class SaveAssemblyProductResult extends GeneralResult {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "产品编号")
    private String productN;

    @ApiModelProperty(value = "产品名字")
    private String enName;

    @ApiModelProperty(value = "产品名字")
    private String cnName;

    @ApiModelProperty(value = "库存数量")
    private Integer stocks;
}
