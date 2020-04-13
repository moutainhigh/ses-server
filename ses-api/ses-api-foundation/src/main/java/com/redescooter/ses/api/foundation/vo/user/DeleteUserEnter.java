package com.redescooter.ses.api.foundation.vo.user;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;

/**
 * @ClassName:DeleteUserEnter
 * @description: DeleteUserEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/18 12:09
 */
@ApiModel(value = "删除用户通过邮箱", description = "删除用户通过邮箱")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class DeleteUserEnter extends GeneralEnter {

    @ApiModelProperty(value = "邮箱")
    private String email;
}
