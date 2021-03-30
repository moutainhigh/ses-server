package com.redescooter.ses.mobile.rps.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.mobile.rps.dm.OpeOrderQcItem;

import java.util.List;

public interface OpeOrderQcItemService extends IService<OpeOrderQcItem> {


    int updateBatch(List<OpeOrderQcItem> list);

    int batchInsert(List<OpeOrderQcItem> list);

    int insertOrUpdate(OpeOrderQcItem record);

    int insertOrUpdateSelective(OpeOrderQcItem record);

}


