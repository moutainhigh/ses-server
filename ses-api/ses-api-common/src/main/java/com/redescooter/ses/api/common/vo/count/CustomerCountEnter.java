package com.redescooter.ses.api.common.vo.count;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @ClassName:CustomerCountEnter
 * @description: CustomerCountEnter
 * @author: Aleks
 * @Version：1.3
 * @create: 2020/12/17 11:04
 */
@Data
public class CustomerCountEnter {

    @ApiModelProperty("类型，1：年，2：月，3：日")
    private Integer type;

    @ApiModelProperty("查询的类型，1：注册量，2：活跃量")
    private Integer queryType;

}
