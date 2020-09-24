package com.redescooter.ses.web.ros.vo.restproduct;

import com.redescooter.ses.api.common.vo.base.PageEnter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassNameRosPartsListEnter
 * @Description
 * @Author Aleks
 * @Date2020/9/23 17:27
 * @Version V1.0
 **/
@Data
public class RosPartsListEnter  extends PageEnter {

    @ApiModelProperty(value = "页面类型,1:草稿，2：部件")
    private Integer classType;

    @ApiModelProperty(value = "sec的id")
    private Long partsSecId;

    @ApiModelProperty(value = "1：Parts，2：Accessory，3：Battery，4：Scooter，5：Combination")
    private Integer partsType;

    @ApiModelProperty("是否可采购，0：否，1：是")
    private Integer snClass;

    @ApiModelProperty("关键字")
    private String keyword;

}
