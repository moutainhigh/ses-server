package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeRepairOrder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional

public interface OpeRepairOrderService extends IService<OpeRepairOrder> {


    int updateBatch(List<OpeRepairOrder> list);

    int batchInsert(List<OpeRepairOrder> list);

    int insertOrUpdate(OpeRepairOrder record);

    int insertOrUpdateSelective(OpeRepairOrder record);

}
