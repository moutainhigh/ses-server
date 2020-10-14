package com.redescooter.ses.web.ros.service.base;

import com.redescooter.ses.web.ros.dm.OpeProductPrice;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OpeProductPriceService extends IService<OpeProductPrice> {

    int updateBatch(List<OpeProductPrice> list);

    int batchInsert(List<OpeProductPrice> list);

    int insertOrUpdate(OpeProductPrice record);

    int insertOrUpdateSelective(OpeProductPrice record);

}
