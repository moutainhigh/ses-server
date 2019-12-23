package com.redescooter.ses.api.foundation.service.base;

import com.redescooter.ses.api.common.vo.base.BaseCustomerResult;
import com.redescooter.ses.api.common.vo.base.DateTimeParmEnter;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import com.redescooter.ses.api.common.vo.base.IdEnter;
import com.redescooter.ses.api.foundation.vo.QueryTenantNodeResult;
import com.redescooter.ses.api.foundation.vo.tenant.SaveTenantConfigEnter;

import java.util.List;

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
     * @param enter
     * @return
     */
    Long saveTenant(DateTimeParmEnter<BaseCustomerResult> enter);

    /**
     * 保存租户节点
     * @param enter
     * @return
     */
    GeneralResult saveTenantNode(DateTimeParmEnter<BaseCustomerResult> enter);

    /**
     * 查询租户节点
     * @param enter
     * @return
     */
    List<QueryTenantNodeResult> queryTenantNdoe(IdEnter enter);

    /**
     * 保存租户默认配置
     * @param enter
     * @return
     */
    GeneralResult saveTenantConfig(SaveTenantConfigEnter enter);
}
