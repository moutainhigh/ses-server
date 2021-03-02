package com.redescooter.ses.mobile.rps.service.base;

import com.redescooter.ses.mobile.rps.dm.OpeLogisticsOrder;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface OpeLogisticsOrderService extends IService<OpeLogisticsOrder>{


    int updateBatch(List<OpeLogisticsOrder> list);

    int batchInsert(List<OpeLogisticsOrder> list);

    int insertOrUpdate(OpeLogisticsOrder record);

    int insertOrUpdateSelective(OpeLogisticsOrder record);

}
