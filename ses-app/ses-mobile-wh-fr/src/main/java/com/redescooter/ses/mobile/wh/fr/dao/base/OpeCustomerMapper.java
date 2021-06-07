package com.redescooter.ses.mobile.wh.fr.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.mobile.wh.fr.dm.OpeCustomer;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OpeCustomerMapper extends BaseMapper<OpeCustomer> {
    int updateBatch(List<OpeCustomer> list);

    int batchInsert(@Param("list") List<OpeCustomer> list);

    int insertOrUpdate(OpeCustomer record);

    int insertOrUpdateSelective(OpeCustomer record);
}