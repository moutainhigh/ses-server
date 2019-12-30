package com.redescooter.ses.service.mobile.b.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.service.mobile.b.dm.base.CorTenantScooter;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CorTenantScooterMapper extends BaseMapper<CorTenantScooter> {
    int updateBatch(List<CorTenantScooter> list);

    int batchInsert(@Param("list") List<CorTenantScooter> list);

    int insertOrUpdate(CorTenantScooter record);

    int insertOrUpdateSelective(CorTenantScooter record);
}