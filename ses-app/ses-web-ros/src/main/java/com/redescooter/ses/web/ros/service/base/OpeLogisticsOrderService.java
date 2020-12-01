package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeLogisticsOrder;

import java.util.List;
public interface OpeLogisticsOrderService extends IService<OpeLogisticsOrder> {


    int updateBatch(List<OpeLogisticsOrder> list);

    int batchInsert(List<OpeLogisticsOrder> list);

    int insertOrUpdate(OpeLogisticsOrder record);

    int insertOrUpdateSelective(OpeLogisticsOrder record);

}
