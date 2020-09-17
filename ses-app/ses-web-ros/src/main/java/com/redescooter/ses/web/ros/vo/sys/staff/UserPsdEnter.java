package com.redescooter.ses.web.ros.vo.sys.staff;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassNameCheckPsdEnter
 * @Description
 * @Author Aleks
 * @Date2020/9/17 10:45
 * @Version V1.0
 **/
@Data
public class UserPsdEnter extends GeneralEnter {

    @ApiModelProperty("密码")
    private  String password;

}
