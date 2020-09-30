package com.redescooter.ses.web.ros.vo.restproduct;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassNameRosPartsBatchOpEnter
 * @Description
 * @Author Aleks
 * @Date2020/9/24 17:07
 * @Version V1.0
 **/
@Data
public class RosPartsBatchOpEnter extends GeneralEnter {

    @ApiModelProperty("id,多个用,隔开")
    private String id;

}
