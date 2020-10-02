package com.redescooter.ses.web.ros.vo.sys.staff;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassNameSafeCodeResult
 * @Description
 * @Author Aleks
 * @Date2020/9/18 13:33
 * @Version V1.0
 **/
@Data
public class SafeCodeResult extends GeneralResult {

    @ApiModelProperty("安全码")
    private String safeCode;

}
