package com.redescooter.ses.web.ros.vo.production;

import com.redescooter.ses.api.common.vo.base.GeneralResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;

/**
 * @ClassName:ConsigneeResult
 * @description: ConsigneeResult
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/18 17:42
 */
@ApiModel(value = "收货人列表", description = "收货人列表")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
public class ConsigneeResult extends GeneralResult {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "名字")
    private String firstName;

    @ApiModelProperty(value = "名字")
    private String lastName;

    @ApiModelProperty(value = "电话")
    private String phone;

}
