package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpePurchasBQc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional

public interface OpePurchasBQcService extends IService<OpePurchasBQc> {


    int updateBatch(List<OpePurchasBQc> list);

    int batchInsert(List<OpePurchasBQc> list);

    int insertOrUpdate(OpePurchasBQc record);

    int insertOrUpdateSelective(OpePurchasBQc record);

}




