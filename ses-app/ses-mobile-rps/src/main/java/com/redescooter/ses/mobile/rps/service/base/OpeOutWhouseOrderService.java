package com.redescooter.ses.mobile.rps.service.base;


import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.mobile.rps.dm.OpeOutWhouseOrder;

import java.util.List;

/**
 * @author assert
 * @date 2021/1/27 22:52
 */
public interface OpeOutWhouseOrderService extends IService<OpeOutWhouseOrder> {

    int updateBatch(List<OpeOutWhouseOrder> list);

    int updateBatchSelective(List<OpeOutWhouseOrder> list);

    int batchInsert(List<OpeOutWhouseOrder> list);

    int insertOrUpdate(OpeOutWhouseOrder record);

    int insertOrUpdateSelective(OpeOutWhouseOrder record);
}


