package com.redescooter.ses.web.ros.vo.log;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassNameLogExportEnter
 * @Description
 * @Author Aleks
 * @Date2020/9/16 20:04
 * @Version V1.0
 **/
@Data
public class LogExportEnter extends GeneralEnter {

    @ApiModelProperty("id,多个用,隔开")
    private String id;

}
