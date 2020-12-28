package com.redescooter.ses.web.ros.vo.wms.cn.china;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.PageEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description:
 * @author: Aleks
 * @create: 2020/12/28 15:58
 */
@Data
@ApiModel("成品库车辆库存入参")
public class WmsFinishScooterListEnter extends PageEnter {

    @ApiModelProperty("车型ID")
    private Long groupId;

    @ApiModelProperty("颜色ID")
    private Long colorId;
}
