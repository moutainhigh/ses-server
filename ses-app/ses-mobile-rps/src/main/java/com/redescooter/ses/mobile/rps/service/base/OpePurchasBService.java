package com.redescooter.ses.mobile.rps.service.base;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.mobile.rps.dm.OpePurchasB;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface OpePurchasBService extends IService<OpePurchasB> {


    int batchInsert(List<OpePurchasB> list);

    int insertOrUpdate(OpePurchasB record);

    int insertOrUpdateSelective(OpePurchasB record);

}

