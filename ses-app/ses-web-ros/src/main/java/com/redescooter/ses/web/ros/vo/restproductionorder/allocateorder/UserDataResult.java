package com.redescooter.ses.web.ros.vo.restproductionorder.allocateorder;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassNameUserDataResult
 * @Description
 * @Author Aleks
 * @Date2020/10/23 20:53
 * @Version V1.0
 **/
@Data
public class UserDataResult extends GeneralResult {

    @ApiModelProperty("人员id")
    private Long id;

    @ApiModelProperty("联系电话")
    private String telephone;

    @ApiModelProperty("邮箱")
    private String mail;

}
