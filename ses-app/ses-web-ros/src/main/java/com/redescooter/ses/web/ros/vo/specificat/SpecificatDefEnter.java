package com.redescooter.ses.web.ros.vo.specificat;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * @ClassNameSpecificatDefEnter
 * @Description
 * @Author Aleks
 * @Date2020/9/28 17:40
 * @Version V1.0
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class SpecificatDefEnter extends GeneralEnter {

    @ApiModelProperty("所属规格id")
    private Long specificatId;

    @ApiModelProperty(value="自定义名称")
    private String defName;

    // 整数部分超过7位数时 会自动转为科学计数法，为避免科学计数法的出现 类型由double换成BigDecimal
    @ApiModelProperty(value="自定义的值")
    private BigDecimal defValue;

}
