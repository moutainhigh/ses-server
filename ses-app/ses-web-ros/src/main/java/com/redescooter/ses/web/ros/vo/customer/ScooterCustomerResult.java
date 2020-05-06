package com.redescooter.ses.web.ros.vo.customer;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @ClassNameCustomerInfoResult
 * @Description
 * @Author Joan
 * @Date2020/4/24 16:48
 * @Version V1.0
 **/
@ApiModel(value = "客户车辆分配信息", description = "创建客户")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class ScooterCustomerResult extends GeneralResult {

    @ApiModelProperty(value = "id")
    private Long id;
    @ApiModelProperty(value = "客户名称")
    private String customerName;
    @ApiModelProperty(value = "邮箱")
    private String email;
    @ApiModelProperty(value = "客户类型")
    private String customerType;
    @ApiModelProperty(value = "客户行业")
    private String industryType;
    @ApiModelProperty(value = "车辆数量")
    private String scooterQuantity;
    @ApiModelProperty(value = "公司名称")
    private String companyName;


}
