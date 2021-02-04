package com.redescooter.ses.web.ros.vo.wms.cn.china;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.PageEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description:
 * @author: Aleks
 * @create: 2020/12/29 18:16
 */
@Data
@ApiModel("原料库部件列表入参")
public class MaterialStockPartsListEnter extends PageEnter {

    @ApiModelProperty(value = "1：Parts，2：Accessory，3：Battery，4：Scooter，5：Combination")
    private Integer partsType;

    @ApiModelProperty("关键字")
    private String keyword;

}
