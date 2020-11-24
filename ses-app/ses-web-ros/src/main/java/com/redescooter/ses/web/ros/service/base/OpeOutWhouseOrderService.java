package com.redescooter.ses.web.ros.service.base;

import java.util.List;

import com.redescooter.ses.web.ros.dm.OpeOutWhouseOrder;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OpeOutWhouseOrderService extends IService<OpeOutWhouseOrder> {


    int updateBatch(List<OpeOutWhouseOrder> list);

    int batchInsert(List<OpeOutWhouseOrder> list);

    int insertOrUpdate(OpeOutWhouseOrder record);

    int insertOrUpdateSelective(OpeOutWhouseOrder record);

}


