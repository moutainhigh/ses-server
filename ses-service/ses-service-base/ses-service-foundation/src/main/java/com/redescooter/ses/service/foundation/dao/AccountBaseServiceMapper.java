package com.redescooter.ses.service.foundation.dao;

import com.redescooter.ses.api.foundation.vo.tenant.QueryAccountListEnter;
import com.redescooter.ses.api.foundation.vo.tenant.QueryAccountListResult;

import java.util.List;

/**
 * @ClassName:AccountBaseServiceMapper
 * @description: AccountBaseServiceMapper
 * @author: Alex
 * @Version：1.3
 * @create: 2019/12/23 18:21
 */
public interface AccountBaseServiceMapper {

    int queryAccountListCount(QueryAccountListEnter enter);

    List<QueryAccountListResult> queryAccountList(QueryAccountListEnter enter);
}
