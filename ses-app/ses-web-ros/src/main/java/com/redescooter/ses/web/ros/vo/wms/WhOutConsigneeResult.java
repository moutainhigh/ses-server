package com.redescooter.ses.web.ros.vo.wms;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;

/**
 * @ClassName:WhOutConsigneeResult
 * @description: WhOutConsigneeResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/07/20 13:57
 */
@ApiModel(value = "收货人出参", description = "收货人出参")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class WhOutConsigneeResult extends GeneralResult {

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "姓名")
    private String firstName;

    @ApiModelProperty(value = "姓名")
    private String lastName;

    @ApiModelProperty(value = "国家代码")
    private String countryCode;

    @ApiModelProperty(value = "电话")
    private String telephone;

    @ApiModelProperty(value = "邮件")
    private String email;
}
