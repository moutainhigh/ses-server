package com.redescooter.ses.web.ros.vo.website;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;

/**
 * @ClassName:ProductModelResult
 * @description: ProductModelResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/05/13 15:42
 */
@ApiModel(value = "Product model", description = "Product model")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class ProductModelResult extends GeneralResult {

    @ApiModelProperty(value = "model Code")
    private String modelCode;

    @ApiModelProperty(value = "name")
    private String name;
}
