package com.redescooter.ses.web.ros.vo.specificat;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassNameColorSaveEnter
 * @Description
 * @Author Aleks
 * @Date2020/9/28 14:53
 * @Version V1.0
 **/
@Data
public class ColorSaveOrEditEnter extends GeneralEnter {

    @ApiModelProperty(value="主键")
    private Long id;

    @ApiModelProperty(value="颜色名称")
    private String colorName;

    @ApiModelProperty(value="色值")
    private String colorValue;


}
