package com.redescooter.ses.service.hub.source.operation.dao;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.service.hub.source.operation.dm.OpeCustomer;
import java.util.List;
import org.apache.ibatis.annotations.Param;

@DS("operation")
public interface OpeCustomerMapper extends BaseMapper<OpeCustomer> {
    int updateBatch(List<OpeCustomer> list);

    int updateBatchSelective(List<OpeCustomer> list);

    int batchInsert(@Param("list") List<OpeCustomer> list);

    int insertOrUpdate(OpeCustomer record);

    int insertOrUpdateSelective(OpeCustomer record);
}