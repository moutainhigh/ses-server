package com.redescooter.ses.web.ros.vo.account;

import com.baomidou.mybatisplus.annotation.TableField;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * description: AddSysUserEnter
 * author: jerry.li
 * create: 2019-05-29 16:45
 */

@ApiModel(value = "ROS添加", description = "ROS添加")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class AddSysUserEnter extends GeneralEnter {

    /**
     * 登录名
     */
    @TableField(value = "LOGIN_NAME")
    @ApiModelProperty(value = "登录名",required = true)
    private String loginName;

    /**
     * 密码
     */
    @TableField(value = "PASSWORD")
    @ApiModelProperty(value = "密码",example = "密码为空时设置默认密码")
    private String password;

}
