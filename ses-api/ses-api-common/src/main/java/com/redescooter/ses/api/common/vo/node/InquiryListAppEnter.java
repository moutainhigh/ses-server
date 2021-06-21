package com.redescooter.ses.api.common.vo.node;

import com.redescooter.ses.api.common.vo.base.PageEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Description
 * @Author Chris
 * @Date 2021/5/11 14:11
 */
@Data
@ApiModel(value = "列表入参")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class InquiryListAppEnter extends PageEnter {

    @ApiModelProperty(value = "状态 1处理中 2已完成")
    private Integer status;

}