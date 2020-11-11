package com.redescooter.ses.web.ros.vo.restproductionorder;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import lombok.*;

import io.swagger.annotations.*;

/**
 * @ClassName:SupplierPrincipaleResult
 * @description: SupplierPrincipaleResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/11/11 15:16 
 */
@ApiModel(value = "工厂负责人出参", description = "工厂负责人出参")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
public class SupplierPrincipaleResult extends GeneralResult {

    @ApiModelProperty(value = "姓")
    private String firstName;

    @ApiModelProperty(value = "名")
    private String lastName;

    @ApiModelProperty(value = "国家代码")
    private String countryCode;

    @ApiModelProperty(value = "电话")
    private String telephone;
}
