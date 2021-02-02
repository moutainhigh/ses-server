package com.redescooter.ses.mobile.rps.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.mobile.rps.dm.OpeQcOrder;
import java.util.List;

/**
 * @author assert
 * @date 2021/1/26 10:56
 */
public interface OpeQcOrderService extends IService<OpeQcOrder> {

    int updateBatch(List<OpeQcOrder> list);

    int updateBatchSelective(List<OpeQcOrder> list);

    int batchInsert(List<OpeQcOrder> list);

    int insertOrUpdate(OpeQcOrder record);

    int insertOrUpdateSelective(OpeQcOrder record);
}


