package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeEntrustOrder;

import java.util.List;

public interface OpeEntrustOrderService extends IService<OpeEntrustOrder> {


    int updateBatch(List<OpeEntrustOrder> list);

    int batchInsert(List<OpeEntrustOrder> list);

    int insertOrUpdate(OpeEntrustOrder record);

    int insertOrUpdateSelective(OpeEntrustOrder record);

}


