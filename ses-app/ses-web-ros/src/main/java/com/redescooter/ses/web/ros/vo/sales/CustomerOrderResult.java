package com.redescooter.ses.web.ros.vo.sales;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName:CustomerOrderResult
 * @description: CustomerOrderResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/12/16 21:06
 */
@Data
public class CustomerOrderResult {

    @ApiModelProperty("日期")
    private String times;

    @ApiModelProperty("数量")
    private Integer total;


}
