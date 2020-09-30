package com.redescooter.ses.web.ros.vo.specificat;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.PageEnter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassNameSpecificatGroupListEnter
 * @Description
 * @Author Aleks
 * @Date2020/9/28 16:12
 * @Version V1.0
 **/
@Data
public class SpecificatGroupListEnter extends PageEnter {

    @ApiModelProperty("分组名称")
    private String groupName;


}
