package com.redescooter.ses.service.foundation.dao;

import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.foundation.vo.tenant.QueryAccountListEnter;
import com.redescooter.ses.api.foundation.vo.tenant.QueryAccountListResult;
import org.apache.ibatis.annotations.Param;

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

    List<CountByStatusResult> accountCountStatus();

    /**
     * ros客户账户列表 状态分组
     *
     * @param personalType
     * @param webRestaurant
     * @param webExpress
     * @return
     */
    List<CountByStatusResult> customerAccountCountByStatus(@Param("personalType") Integer personalType, @Param("webRestaurant") Integer webRestaurant, @Param("webExpress") Integer webExpress);
}
