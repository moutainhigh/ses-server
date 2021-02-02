package com.redescooter.ses.mobile.rps.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.mobile.rps.dm.OpeQcCombinB;
import java.util.List;

/**
 * @author assert
 * @date 2021/1/26 15:38
 */
public interface OpeQcCombinBService extends IService<OpeQcCombinB> {

    int updateBatch(List<OpeQcCombinB> list);

    int updateBatchSelective(List<OpeQcCombinB> list);

    int batchInsert(List<OpeQcCombinB> list);

    int insertOrUpdate(OpeQcCombinB record);

    int insertOrUpdateSelective(OpeQcCombinB record);
}


