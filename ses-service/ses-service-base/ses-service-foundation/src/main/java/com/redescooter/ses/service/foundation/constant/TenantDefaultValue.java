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
    Long DISTRIBUTION_RANGE = 10L;

    // 默认配送预警时间
    Long ESTIMATED_DURATION = 30L;

    // 默认超时时间
    Long TIMEOUT_EXPECTDE = 15L;

    String TENANT_LONGITUDE = "";

    String TENANT_Latitude = "";

    Long DEFAULT_MOBILEC_TENANTID = 0L;

    String BEGIN_TIME ="2020-1-10 10:00:00";

    String END_TIME ="2020-1-10 16:00:00";

    // 1 表示营业 2、 表示打烊
    String BUSSINESS_STATUS="1";
}
