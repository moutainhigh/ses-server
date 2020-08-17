package com.redescooter.ses.web.ros.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.web.ros.dm.OpeDistrustLead;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpeDistrustLeadMapper extends BaseMapper<OpeDistrustLead> {
    int updateBatch(List<OpeDistrustLead> list);

    int updateBatchSelective(List<OpeDistrustLead> list);

    int batchInsert(@Param("list") List<OpeDistrustLead> list);

    int insertOrUpdate(OpeDistrustLead record);

    int insertOrUpdateSelective(OpeDistrustLead record);
}