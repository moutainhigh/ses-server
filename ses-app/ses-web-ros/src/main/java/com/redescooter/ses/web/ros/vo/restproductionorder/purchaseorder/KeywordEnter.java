package com.redescooter.ses.web.ros.vo.restproductionorder.purchaseorder;

import com.redescooter.ses.api.common.annotation.NotEmpty;
import com.redescooter.ses.api.common.annotation.NotNull;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.web.ros.exception.ValidationExceptionCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassNameKeywordEnter
 * @Description
 * @Author Aleks
 * @Date2020/10/28 10:07
 * @Version V1.0
 **/
@ApiModel(value = "关键字搜索",description = "关键字搜索")
@Data
public class KeywordEnter extends GeneralEnter {

    @ApiModelProperty("查询条件")
    private String keyword;

    @ApiModelProperty("类型,1:车辆，2:组装件，3:部件")
    @NotEmpty(code = ValidationExceptionCode.TYPE_IS_EMPTY, message = "类型为空")
    private Integer classType;

}
