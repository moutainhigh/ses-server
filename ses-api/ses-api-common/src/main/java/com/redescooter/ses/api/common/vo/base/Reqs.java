package com.redescooter.ses.api.common.vo.base;

import lombok.*;

import java.io.Serializable;

/**
 * Class description
 *
 * @author: Darren
 * @create: 2018-08-20 00:19
 */
@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class Reqs implements Serializable {
    private static final long serialVersionUID = 6799682561585226797L;

    private String mobile;
    private String userId;
}
