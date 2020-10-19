package com.redescooter.ses.web.ros.vo.specificat;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassNameSpecificatTypeData
 * @Description
 * @Author Aleks
 * @Date2020/10/13 15:58
 * @Version V1.0
 **/
@Data
public class SpecificatTypeDataResult extends GeneralResult {


    @ApiModelProperty("规格id")
    private Long specificatId;

    @ApiModelProperty(value = "规格名称")
    private String specificatName;

}
