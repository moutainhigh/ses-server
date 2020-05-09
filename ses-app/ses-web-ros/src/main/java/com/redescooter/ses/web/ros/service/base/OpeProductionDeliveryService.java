package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeProductionDelivery;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional

public interface OpeProductionDeliveryService extends IService<OpeProductionDelivery> {


    int updateBatch(List<OpeProductionDelivery> list);

    int batchInsert(List<OpeProductionDelivery> list);

    int insertOrUpdate(OpeProductionDelivery record);

    int insertOrUpdateSelective(OpeProductionDelivery record);

}
