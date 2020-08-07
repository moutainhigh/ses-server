package com.redescooter.ses.service.foundation.dao;

import com.redescooter.ses.api.common.vo.CountByStatusResult;
import com.redescooter.ses.api.foundation.vo.tenant.QueryAccountCountStatusEnter;
import com.redescooter.ses.api.foundation.vo.tenant.QueryAccountListEnter;
import com.redescooter.ses.api.foundation.vo.tenant.QueryAccountResult;
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

  List<CountByStatusResult> accountCountStatus();

    /**
     * ros客户账户列表 状态分组
     *
     * @param accountType
     * @return
     */
    List<CountByStatusResult> customerAccountCountByStatus(@Param("enter")QueryAccountCountStatusEnter enter,@Param("accountType") List<Integer> accountType);

    /**
     * 查询客户账户列表统计
     *
     * @param enter
     * @return
     */
    int customerAccountCount(@Param("enter") QueryAccountListEnter enter, @Param("accountType") List<Integer> accountType);

    /**
     * 查询客户账户列表
     *
     * @param enter
     * @return
     */
    List<QueryAccountResult> customerAccountList(@Param("enter") QueryAccountListEnter enter, @Param("accountType") List<Integer> accountType);

    /**
     * 客户账户详情
     *
     * @param email
     * @param accountType
     * @return
     */
    QueryAccountResult customerAccountDeatil(@Param("email") String email, @Param("accountType") List<Integer> accountType);
}
