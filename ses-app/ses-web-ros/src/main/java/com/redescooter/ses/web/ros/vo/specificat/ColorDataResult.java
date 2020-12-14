package com.redescooter.ses.web.ros.vo.specificat;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassNameColorDataResult
 * @Description
 * @Author Aleks
 * @Date2020/9/28 15:30
 * @Version V1.0
 **/
@Data
public class ColorDataResult extends GeneralResult {

    @ApiModelProperty(value="主键")
    private Long colorId;

    @ApiModelProperty(value="颜色名称")
    private String colorName;

    @ApiModelProperty(value="色值")
    private String colorValue;

}
