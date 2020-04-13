package com.redescooter.ses.web.ros.vo.production.allocate;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @ClassName:SaveAssemblyProductEnter
 * @description: SaveAssemblyProductEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/31 16:24
 */
@ApiModel(value = "查询组装商品", description = "查询组装商品")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class SaveAssemblyProductEnter extends GeneralEnter {

    @ApiModelProperty(value = "产品信息 格式:id：100，qty：123")
    private String productList;

}
