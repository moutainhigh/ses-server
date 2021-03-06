package com.redescooter.ses.web.ros.vo.restproductionorder.allocateorder;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassNameUserDataEnter
 * @Description
 * @Author Aleks
 * @Date2020/10/23 20:56
 * @Version V1.0
 **/
@Data
@ApiModel(value = "收货人、发货人下拉入参", description = "收货人、通知人、发货人下拉入参")
public class UserDataEnter extends GeneralEnter {

    @ApiModelProperty("查询条件")
    private String keyword;

}
