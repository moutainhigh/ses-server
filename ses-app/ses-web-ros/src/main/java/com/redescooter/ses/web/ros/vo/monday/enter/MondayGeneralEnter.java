package com.redescooter.ses.web.ros.vo.monday.enter;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Date;

/**
 * @ClassName:MondayGeneralEnter
 * @description: MondayGeneralEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/08/14 16:45
 */
@ApiModel(value = "Monday业务基础入参", description = "Monday业务基础入参")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
@Builder
public class MondayGeneralEnter<T> extends GeneralEnter {

    @ApiModelProperty(value = "姓名")
    private String firstName;

    @ApiModelProperty(value = "姓名")
    private String lastName;

    @ApiModelProperty(value = "电话")
    private String telephone;

    @ApiModelProperty(value = "创建时间")
    private Date createdTime;
    
    @ApiModelProperty(value = "更新时间")
    private Date updatedTime;

    @ApiModelProperty(value = "邮件")
    private String email;

    @ApiModelProperty(value = "城市")
    private String city;
    
    @ApiModelProperty(value = "区域")
    private String distant;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "备注")
    private String remarks;
    
    @ApiModelProperty(value = "业务对象")
    private T t;
    
}
