package com.redescooter.ses.web.ros.vo.specificat;

import com.redescooter.ses.api.common.vo.base.PageEnter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassNameSpecificatTypeListEnter
 * @Description
 * @Author Aleks
 * @Date2020/9/28 18:43
 * @Version V1.0
 **/
@Data
public class SpecificatTypeListEnter extends PageEnter {

    @ApiModelProperty(value = "所属分组")
    private Long groupId;

    @ApiModelProperty("关键字")
    private String keyword;


}
