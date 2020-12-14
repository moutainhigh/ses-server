package com.redescooter.ses.web.ros.vo.deliveryopion;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Date;

@ApiModel(value = "Delivery Option Save Enter", description = "Delivery Option Save Enter")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class DeliveryOptionSaveResult extends GeneralResult {

    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "Status identification [0 normal, 1 invalid]")
    private Integer status;

    @ApiModelProperty(value = "name")
    private String optionNeme;

    @ApiModelProperty(value = "price")
    private Double price;

    @ApiModelProperty(value = "memo")
    private String memo;

    @ApiModelProperty(value = "created time")
    private Date createdTime;

    @ApiModelProperty(value = "Custom fields")
    private String def1;
}
