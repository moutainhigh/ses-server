package com.redescooter.ses.web.ros.vo.wms.cn.china;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @description:
 * @author: Aleks
 * @create: 2020/12/29 11:58
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("仓库的*类型")
public class WmsStockTypeEnter extends GeneralEnter {

    @ApiModelProperty("仓库类型，1：中国仓库，2：法国仓库")
    private Integer stockType;

    @ApiModelProperty(value = "是否是不合格品库产生，0:否，1:是")
    private Integer source;

    @ApiModelProperty(value = "入库单据类型,1:车辆，2:组装件，3:部件")
    private Integer orderType;

}
