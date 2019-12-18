package com.redescooter.ses.api.foundation.vo.user;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import lombok.*;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 24/9/2019 8:27 上午
 * @ClassName: GetUserEnter
 * @Function: TODO
 */
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class GetUserEnter extends GeneralEnter {

    private String loginName;

    private String email;

    private Integer accountType;
}
