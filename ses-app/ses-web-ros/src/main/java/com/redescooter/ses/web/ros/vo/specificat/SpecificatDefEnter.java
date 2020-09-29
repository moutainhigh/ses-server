package com.redescooter.ses.web.ros.vo.specificat;

import com.baomidou.mybatisplus.annotation.TableField;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassNameSpecificatDefEnter
 * @Description
 * @Author Aleks
 * @Date2020/9/28 17:40
 * @Version V1.0
 **/
@Data
public class SpecificatDefEnter extends GeneralEnter {

    @ApiModelProperty("所属规格id")
    private Long specificatId;

    @ApiModelProperty(value="自定义名称")
    private String defName;

    @ApiModelProperty(value="自定义的值")
    private double defValue;

}
