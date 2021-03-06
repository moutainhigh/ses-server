package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeOrderStatusFlow;

import java.util.List;

public interface OpeOrderStatusFlowService extends IService<OpeOrderStatusFlow> {


    int updateBatch(List<OpeOrderStatusFlow> list);

    int batchInsert(List<OpeOrderStatusFlow> list);

    int insertOrUpdate(OpeOrderStatusFlow record);

    int insertOrUpdateSelective(OpeOrderStatusFlow record);

}

