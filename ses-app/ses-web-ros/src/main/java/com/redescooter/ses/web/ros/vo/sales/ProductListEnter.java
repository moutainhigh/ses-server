package com.redescooter.ses.web.ros.vo.sales;

import java.util.Date;

import com.redescooter.ses.api.common.vo.base.PageEnter;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @ClassName:ProductListEnter
 * @description: ProductListEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/02/25 17:24
 */
@ApiModel(value = "销售产品列表", description = "销售产品列表")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class ProductListEnter extends PageEnter {

    @ApiModelProperty(value = "产品类型")
    private String type;

    @ApiModelProperty(value = "刷新开始时间")
    private Date refuseStartTime;

    @ApiModelProperty(value = "刷新结束时间")
    private Date refuseEndTime;

    @ApiModelProperty(value = "关键字查询")
    private String keyword;
}
