package com.redescooter.ses.web.ros.vo.sellsy.enter.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;

/**
 * @ClassName:SellsyCreateClientAddressEnter
 * @description: SellsyCreateClientAddressEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/08/25 09:52
 */
@ApiModel(value = "客户创建地址", description = "客户创建地址")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class SellsyCreateClientAddressEnter {
    @ApiModelProperty(value = "address name",required = true)
    private String name;
    
    @ApiModelProperty(value = "Address part 1")
    private String part1;
    
    @ApiModelProperty(value = "Address part 2")
    private String part2;
    
    @ApiModelProperty(value = "Address part 3")
    private String part3;
    
    @ApiModelProperty(value = "Address part 4")
    private String part4;
    
    @ApiModelProperty(value = "Address post code")
    private String zip;
    
    @ApiModelProperty(value = "City")
    private String town;
    
    @ApiModelProperty(value = "Country code 默认值 FR")
    private String countrycode;
}
