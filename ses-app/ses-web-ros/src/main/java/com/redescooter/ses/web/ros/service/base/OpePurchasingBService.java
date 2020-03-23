package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpePurchasingB;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface OpePurchasingBService extends IService<OpePurchasingB> {


    int batchInsert(List<OpePurchasingB> list);

    int insertOrUpdate(OpePurchasingB record);

    int insertOrUpdateSelective(OpePurchasingB record);

}

