package com.redescooter.ses.web.ros.vo.wms.cn.china;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description:
 * @author: Aleks
 * @create: 2021/01/14 18:06
 */
@Data
public class WmsQualifiedCombinEnter extends GeneralEnter {

    @ApiModelProperty(value = "是否是不合格品库产生，0:否，1:是")
    private Integer source = 0;

    @ApiModelProperty("id")
    private Long id;

}
