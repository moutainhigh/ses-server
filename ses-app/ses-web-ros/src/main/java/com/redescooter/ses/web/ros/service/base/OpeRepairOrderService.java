package com.redescooter.ses.web.ros.service.base;

import java.util.List;

import com.redescooter.ses.web.ros.dm.OpeRepairOrder;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OpeRepairOrderService extends IService<OpeRepairOrder> {


    int updateBatch(List<OpeRepairOrder> list);

    int batchInsert(List<OpeRepairOrder> list);

    int insertOrUpdate(OpeRepairOrder record);

    int insertOrUpdateSelective(OpeRepairOrder record);

}
