package com.redescooter.ses.mobile.wh.fr.vo;

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
@ApiModel(value = "询价单列表入参")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class InquiryListAppEnter extends PageEnter {

    @ApiModelProperty(value = "询价单状态 1待处理 2处理中 3已完成")
    private Integer status;

    @ApiModelProperty(value = "关键字")
    private String keyword;

}