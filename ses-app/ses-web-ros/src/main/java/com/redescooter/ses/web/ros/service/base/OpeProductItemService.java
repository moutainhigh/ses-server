package com.redescooter.ses.web.ros.service.base;

import java.util.List;

import com.redescooter.ses.web.ros.dm.OpeProductItem;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OpeProductItemService extends IService<OpeProductItem> {


    int updateBatch(List<OpeProductItem> list);

    int batchInsert(List<OpeProductItem> list);

    int insertOrUpdate(OpeProductItem record);

    int insertOrUpdateSelective(OpeProductItem record);

}

