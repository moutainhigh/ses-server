package com.redescooter.ses.api.common.vo.count;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @ClassName:CustomerCountResult
 * @description: CustomerCountResult
 * @author: Aleks
 * @Version：1.3
 * @create: 2020/12/17 11:22
 */
@Data
@ApiModel(value = "OMS客户统计出参")
public class CustomerCountResult extends GeneralResult {

    @ApiModelProperty("客户类型，1：saas,2:app-ToB,3:app-ToC")
    private Integer customerType;

//    @ApiModelProperty("数量")
//    private Integer totalCount = 0;

    @ApiModelProperty("注册量or活跃量")
    private Integer count = 0;


}
