package com.redescooter.ses.service.foundation.constant;

/**
 * @ClassName:TenantDefaultValue
 * @description: TenantDefaultValue
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/05 19:10
 */
public interface TenantDefaultValue {
    // 餐厅默认配送距离
    Long DISTRIBUTION_RANGE = 5L;

    // 默认配送预警时间
    Long ESTIMATED_DURATION = 45L;

    // 默认超时时间
    Long TIMEOUT_EXPECTDE = 45L;

    String TENANT_LONGITUDE = "";

    String TENANT_Latitude = "";

    Long DEFAULT_MOBILEC_TENANTID = 0L;

    String BEGIN_TIME ="2020-1-10 10:00:00";

    String END_TIME ="2020-1-10 16:00:00";
}
