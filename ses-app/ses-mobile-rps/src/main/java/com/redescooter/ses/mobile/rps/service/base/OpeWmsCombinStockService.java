package com.redescooter.ses.mobile.rps.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.mobile.rps.dm.OpeWmsCombinStock;
import java.util.List;

/**
 * @author assert
 * @date 2021/1/14 15:44
 */
public interface OpeWmsCombinStockService extends IService<OpeWmsCombinStock> {

    int updateBatch(List<OpeWmsCombinStock> list);

    int updateBatchSelective(List<OpeWmsCombinStock> list);

    int batchInsert(List<OpeWmsCombinStock> list);

    int insertOrUpdate(OpeWmsCombinStock record);

    int insertOrUpdateSelective(OpeWmsCombinStock record);
}


