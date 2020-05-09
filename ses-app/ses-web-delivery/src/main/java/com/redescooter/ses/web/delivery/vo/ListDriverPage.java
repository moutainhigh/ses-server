package com.redescooter.ses.web.delivery.vo;

import com.redescooter.ses.api.common.vo.base.PageEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 2/1/2020 10:50 上午
 * @ClassName: ListDriverPage
 * @Function: TODO
 */
@ApiModel(value = "司机列表入参", description = "司机列表入参")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class ListDriverPage extends PageEnter {

    @ApiModelProperty(value = "状态")
    private String status;
    @ApiModelProperty(value = "关键字")
    private String keyword;
    @ApiModelProperty(value = "是否在map 调用，默认false")
    private Boolean map = Boolean.FALSE;
}
