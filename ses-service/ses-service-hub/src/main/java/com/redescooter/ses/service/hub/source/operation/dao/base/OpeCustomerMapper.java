package com.redescooter.ses.service.hub.source.operation.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.service.hub.source.operation.dm.OpeCustomer;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface OpeCustomerMapper extends BaseMapper<OpeCustomer> {
    int updateBatch(List<OpeCustomer> list);

    int updateBatchSelective(List<OpeCustomer> list);

    int batchInsert(@Param("list") List<OpeCustomer> list);

    int insertOrUpdate(OpeCustomer record);

    int insertOrUpdateSelective(OpeCustomer record);
}