package com.redescooter.ses.web.ros.service.base;

import java.util.List;
import com.redescooter.ses.web.ros.dm.OpeDistrustLead;
import com.baomidou.mybatisplus.extension.service.IService;
public interface OpeDistrustLeadService extends IService<OpeDistrustLead>{


    int updateBatch(List<OpeDistrustLead> list);

    int updateBatchSelective(List<OpeDistrustLead> list);

    int batchInsert(List<OpeDistrustLead> list);

    int insertOrUpdate(OpeDistrustLead record);

    int insertOrUpdateSelective(OpeDistrustLead record);

}
