package com.redescooter.ses.mobile.rps.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.mobile.rps.dm.OpeQcPartsB;
import java.util.List;

/**
 * @author assert
 * @date 2021/1/26 15:38
 */
public interface OpeQcPartsBService extends IService<OpeQcPartsB> {

    int updateBatch(List<OpeQcPartsB> list);

    int updateBatchSelective(List<OpeQcPartsB> list);

    int batchInsert(List<OpeQcPartsB> list);

    int insertOrUpdate(OpeQcPartsB record);

    int insertOrUpdateSelective(OpeQcPartsB record);
}


