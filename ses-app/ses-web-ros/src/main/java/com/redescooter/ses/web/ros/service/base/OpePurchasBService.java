package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpePurchasB;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional

public interface OpePurchasBService extends IService<OpePurchasB> {


    int batchInsert(List<OpePurchasB> list);

    int insertOrUpdate(OpePurchasB record);

    int insertOrUpdateSelective(OpePurchasB record);

}


