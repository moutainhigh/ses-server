package com.redescooter.ses.web.delivery.vo;

import com.redescooter.ses.api.common.vo.base.PageEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName:QueryExpressDeliveryByPageEnter
 * @description: QueryExpressDeliveryByPageEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2019/09/12 15:44
 */
@ApiModel(value = "order 分页入参", description = "order 分页入参")
@Data
@EqualsAndHashCode(callSuper = false)
public class QueryExpressOrderByPageEnter extends PageEnter {

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "城市")
    private String city;

    @ApiModelProperty(value = "zipcode")
    private String postalCode;

    @ApiModelProperty(value = "创建开始时间")
    private String creatStartTime;

    @ApiModelProperty(value = "创建结束时间")
    private String creatEndTime;

    @ApiModelProperty(value = "完成开始时间")
    private String deliveredStartTime;

    @ApiModelProperty(value = "完成结束时间")
    private String deliveredEndTime;

    @ApiModelProperty(value = "关键字")
    private String keyword;
}
