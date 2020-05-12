package com.redescooter.ses.mobile.rps.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.rps.dm.OpeCustomer;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpeCustomerMapper extends BaseMapper<OpeCustomer> {
    int updateBatch(List<OpeCustomer> list);

    int batchInsert(@Param("list") List<OpeCustomer> list);

    int insertOrUpdate(OpeCustomer record);

    int insertOrUpdateSelective(OpeCustomer record);
}