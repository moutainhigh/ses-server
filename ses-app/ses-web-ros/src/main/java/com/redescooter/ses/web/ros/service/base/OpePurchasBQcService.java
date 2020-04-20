package com.redescooter.ses.web.ros.service.base;

import java.util.List;
import com.redescooter.ses.web.ros.dm.OpePurchasBQc;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface OpePurchasBQcService extends IService<OpePurchasBQc>{


    int updateBatch(List<OpePurchasBQc> list);

    int batchInsert(List<OpePurchasBQc> list);

    int insertOrUpdate(OpePurchasBQc record);

    int insertOrUpdateSelective(OpePurchasBQc record);

}
