package com.redescooter.ses.web.ros.vo.wms.cn.china;

import com.redescooter.ses.api.common.annotation.NotEmpty;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.ros.exception.ValidationExceptionCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description:
 * @author: Aleks
 * @create: 2020/12/30 14:28
 */
@Data
@ApiModel("不合格品库库存数量统计入参")
public class WmsQualifiedQtyCountEnter extends GeneralEnter {

    @ApiModelProperty("类型,1:车辆，2:组装件，3:部件")
    @NotEmpty(code = ValidationExceptionCode.TYPE_IS_EMPTY, message = "类型为空")
    private Integer classType;

}
