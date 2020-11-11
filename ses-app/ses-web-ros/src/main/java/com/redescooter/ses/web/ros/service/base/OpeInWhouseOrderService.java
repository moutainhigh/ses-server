package com.redescooter.ses.web.ros.service.base;

import java.util.List;
import com.redescooter.ses.web.ros.dm.OpeInWhouseOrder;
import com.baomidou.mybatisplus.extension.service.IService;
public interface OpeInWhouseOrderService extends IService<OpeInWhouseOrder>{


    int updateBatch(List<OpeInWhouseOrder> list);

    int batchInsert(List<OpeInWhouseOrder> list);

    int insertOrUpdate(OpeInWhouseOrder record);

    int insertOrUpdateSelective(OpeInWhouseOrder record);

}
