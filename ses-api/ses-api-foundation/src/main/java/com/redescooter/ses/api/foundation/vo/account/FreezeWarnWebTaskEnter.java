package com.redescooter.ses.api.foundation.vo.account;

import com.redescooter.ses.api.common.vo.base.BaseMailTaskEnter;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Date;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 12/10/2019 5:04 下午
 * @ClassName: FreezeWarnWebTaskEnter
 * @Function: TODO
 */
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class FreezeWarnWebTaskEnter extends BaseMailTaskEnter {

    @ApiModelProperty(value = "冻结日期")
    private Date freezeDate;
}
