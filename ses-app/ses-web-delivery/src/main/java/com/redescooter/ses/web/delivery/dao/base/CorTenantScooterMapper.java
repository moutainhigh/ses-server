package com.redescooter.ses.web.delivery.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.delivery.dm.CorTenantScooter;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CorTenantScooterMapper extends BaseMapper<CorTenantScooter> {
    int updateBatch(List<CorTenantScooter> list);

    int batchInsert(@Param("list") List<CorTenantScooter> list);

    int insertOrUpdate(CorTenantScooter record);

    int insertOrUpdateSelective(CorTenantScooter record);
}