package com.redescooter.ses.web.website.vo.distributor;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author jerry
 * @Date 2021/1/6 3:43 上午
 * @Description 新增经销商入参
 **/
@ApiModel(value = "新增经销商入参", description = "新增经销商入参")
@Data
@EqualsAndHashCode(callSuper = true)
public class QueryDistributorEnter extends GeneralEnter {

    /**
     * 关键字查询
     */
    @ApiModelProperty(value = "关键字查询")
    private String keyword;

    /**
     * 经度
     */
    @ApiModelProperty(value = "经度")
    private String longitude;

    /**
     * 纬度
     */
    @ApiModelProperty(value = "纬度")
    private String latitude;

    /**
     * 地图搜索距离范围
     */
    @ApiModelProperty(value = "距离范围")
    private String distanceRange;

}
