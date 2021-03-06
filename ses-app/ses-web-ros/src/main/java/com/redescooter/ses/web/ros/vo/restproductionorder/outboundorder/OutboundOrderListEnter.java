package com.redescooter.ses.web.ros.vo.restproductionorder.outboundorder;

import com.redescooter.ses.api.common.vo.base.PageEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 *  @author: alex
 *  @Date: 2020/10/22 13:48
 *  @version：V ROS 1.8.3
 *  @Description:
 */
@ApiModel(value = "出库单列表", description = "出库单列表")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class OutboundOrderListEnter extends PageEnter {

    @ApiModelProperty(value = "状态")
    private Integer outWhStatus;

    @ApiModelProperty(value = "关键字")
    private String keyword;

    @ApiModelProperty("类型")
    private Integer outType;

    @ApiModelProperty(value = "单据类型 参考 ProductTypeEnums")
    private Integer classType;

    @ApiModelProperty(value = "国家类型，1:中国，2:法国")
    private Integer countryType;

    @ApiModelProperty(value = "是否是不合格品库产生，0:否，1:是")
    private Integer source;
}
