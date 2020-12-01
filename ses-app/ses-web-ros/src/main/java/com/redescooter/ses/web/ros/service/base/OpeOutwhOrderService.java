package com.redescooter.ses.web.ros.service.base;

import com.redescooter.ses.web.ros.dm.OpeOutwhOrder;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OpeOutwhOrderService extends IService<OpeOutwhOrder> {


    int updateBatch(List<OpeOutwhOrder> list);

    int batchInsert(List<OpeOutwhOrder> list);

    int insertOrUpdate(OpeOutwhOrder record);

    int insertOrUpdateSelective(OpeOutwhOrder record);

}
