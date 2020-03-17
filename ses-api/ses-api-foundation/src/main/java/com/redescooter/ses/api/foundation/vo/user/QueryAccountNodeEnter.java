package com.redescooter.ses.api.foundation.vo.user;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import io.swagger.annotations.*;

/**
 * @ClassName:CustomerAccountNodeDetailEnter
 * @description: CustomerAccountNodeDetailEnter
 * @author: Alex
 * @Version：1.3
 * @create: 2020/03/17 15:51
 */
@ApiModel(value = "", description = "")
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class QueryAccountNodeEnter extends GeneralEnter {
    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "登录账户")
    private String email;
}
