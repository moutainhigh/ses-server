package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpePurchasingBQc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface OpePurchasingBQcService extends IService<OpePurchasingBQc> {


    int updateBatch(List<OpePurchasingBQc> list);

    int batchInsert(List<OpePurchasingBQc> list);

    int insertOrUpdate(OpePurchasingBQc record);

    int insertOrUpdateSelective(OpePurchasingBQc record);

}


