package com.redescooter.ses.mobile.wh.fr.vo;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Description
 * @Author Chris
 * @Date 2021/5/11 16:59
 */
@Data
@ApiModel(value = "询价单详情入参")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class InquiryDetailEnter extends GeneralEnter {

    @ApiModelProperty(value = "询价单id")
    private Long id;

    @ApiModelProperty(value = "客户id")
    private Long customerId;

}
