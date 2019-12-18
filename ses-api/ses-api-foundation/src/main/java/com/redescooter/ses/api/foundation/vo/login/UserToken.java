package com.redescooter.ses.api.foundation.vo.login;


import com.redescooter.ses.api.common.vo.base.GeneralResult;
import lombok.*;

/**
 * @description: UserToken
 * @author: Darren
 * @create: 2019/01/18 10:10
 */
@Data
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class UserToken extends GeneralResult {

    private String token;

    private String systemId;

    private String appId;

    private Long tenantId;

    private Long userId;

    private String version;

    private long timestamp;

    private String clientType;

    private String clientIp;

    private String timeZone;

    private String language;

    private String country;
}
