package com.redescooter.ses.web.ros.vo.wms.cn.china;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @description:
 * @author: Aleks
 * @create: 2021/01/11 19:13
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OutOrInWhConfirmEnter extends GeneralEnter {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("类型，1：整车，2：组装件，3：部件")
    private Integer type;

    @ApiModelProperty("仓库类型，1：中国仓库，2：法国仓库")
    private Integer stockType;

}
