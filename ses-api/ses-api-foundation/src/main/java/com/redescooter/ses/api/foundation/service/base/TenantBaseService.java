package com.redescooter.ses.api.foundation.service.base;

import com.redescooter.ses.api.common.vo.base.BaseCustomerResult;
import com.redescooter.ses.api.common.vo.base.DateTimeParmEnter;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.foundation.vo.account.QueryTenantNodeResult;
import com.redescooter.ses.api.foundation.vo.tenant.QueryTenantResult;
import com.redescooter.ses.api.foundation.vo.tenant.SaveTenantConfigEnter;
import com.redescooter.ses.api.foundation.vo.tenant.TenantConfigInfoResult;

import java.util.List;
import java.util.Map;

/**
 * @ClassName:TenantBaseService
 * @description: TenantBaseService
 * @author: Alex
 * @Version：1.0
 * @create: 2019/12/23 13:57
 */
public interface TenantBaseService {
    /**
     * 保存租户
     *
     * @param enter
     * @return
     */
    Long saveTenant(DateTimeParmEnter<BaseCustomerResult> enter);

    /**
     * 保存租户节点
     *
     * @param enter
     * @return
     */
    GeneralResult saveTenantNode(DateTimeParmEnter<BaseCustomerResult> enter, String event);

    /**
     * 查询租户节点
     *
     * @param enter
     * @return
     */
    List<QueryTenantNodeResult> queryTenantNdoe(IdEnter enter);

    /**
     * 保存租户默认配置
     *
     * @param enter
     * @return
     */
    GeneralResult saveTenantConfig(SaveTenantConfigEnter enter);

    /**
     * 店铺配置
     *
     * @param enter
     * @return
     */
    TenantConfigInfoResult tenantConfigInfo(GeneralEnter enter);

    /**
     * 查询租户信息
     *
     * @param enter
     * @return
     */
    QueryTenantResult queryTenantById(IdEnter enter);

    /**
     * 查询租户状态
     *
     * @return
     */
    Map<String, Integer> accountCountStatus();
}
