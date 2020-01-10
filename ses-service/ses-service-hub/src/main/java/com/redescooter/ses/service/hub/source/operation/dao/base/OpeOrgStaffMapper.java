package com.redescooter.ses.service.hub.source.operation.dao.base;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.service.hub.source.operation.dm.OpeOrgStaff;
import java.util.List;
import org.apache.ibatis.annotations.Param;

@DS("operation")
public interface OpeOrgStaffMapper extends BaseMapper<OpeOrgStaff> {
    int updateBatch(List<OpeOrgStaff> list);

    int batchInsert(@Param("list") List<OpeOrgStaff> list);

    int insertOrUpdate(OpeOrgStaff record);

    int insertOrUpdateSelective(OpeOrgStaff record);
}