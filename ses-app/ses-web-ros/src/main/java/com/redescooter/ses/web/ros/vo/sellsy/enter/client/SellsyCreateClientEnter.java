package com.redescooter.ses.web.ros.vo.sellsy.enter.client;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @ClassName:SellsyCreateClientEnter
 * @description: SellsyCreateClientEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/08/24 18:55
 */
@ApiModel(value = "客户创建入参", description = "")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class SellsyCreateClientEnter {

    @ApiModelProperty(value = "客户信息")
    private SellsyCreateClientThirdEnter third;
    
    @ApiModelProperty(value = "")//Contact the parameter is required only if the type of the third is person
    private SellsyCreateClientContactEnter contact;
    
    @ApiModelProperty(value = "")//The address parameter is not mandatory
    private SellsyCreateClientAddressEnter address;
    
}
