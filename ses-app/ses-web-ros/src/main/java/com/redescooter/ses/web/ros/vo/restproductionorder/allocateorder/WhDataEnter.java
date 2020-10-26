package com.redescooter.ses.web.ros.vo.restproductionorder.allocateorder;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassNameWhDataEnter
 * @Description
 * @Author Aleks
 * @Date2020/10/26 19:00
 * @Version V1.0
 **/
@Data
public class WhDataEnter extends GeneralEnter {

    @ApiModelProperty("类型，1：发货仓库，2：接受仓库")
    private Integer type;

}
