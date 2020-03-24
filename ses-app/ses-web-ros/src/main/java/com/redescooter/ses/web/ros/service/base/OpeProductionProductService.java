package com.redescooter.ses.web.ros.service.base;

import java.util.List;

import com.redescooter.ses.web.ros.dm.OpeProductionProduct;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OpeProductionProductService extends IService<OpeProductionProduct> {


    int updateBatch(List<OpeProductionProduct> list);

    int batchInsert(List<OpeProductionProduct> list);

    int insertOrUpdate(OpeProductionProduct record);

    int insertOrUpdateSelective(OpeProductionProduct record);

}
