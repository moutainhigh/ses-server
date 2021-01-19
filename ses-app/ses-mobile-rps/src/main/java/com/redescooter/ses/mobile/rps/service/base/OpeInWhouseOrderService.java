package com.redescooter.ses.mobile.rps.service.base;

import com.redescooter.ses.mobile.rps.dm.OpeInWhouseOrder;
import java.util.List;

/**
 * @author assert
 * @date 2021/1/18 10:46
 */
public interface OpeInWhouseOrderService {


    int updateBatch(List<OpeInWhouseOrder> list);

    int batchInsert(List<OpeInWhouseOrder> list);

    int insertOrUpdate(OpeInWhouseOrder record);

    int insertOrUpdateSelective(OpeInWhouseOrder record);

}

