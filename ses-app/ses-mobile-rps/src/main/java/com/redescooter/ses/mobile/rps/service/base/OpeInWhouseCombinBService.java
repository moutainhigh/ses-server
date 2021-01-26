package com.redescooter.ses.mobile.rps.service.base;

import java.util.List;
import com.redescooter.ses.mobile.rps.dm.OpeInWhouseCombinB;

/**
 * @author assert
 * @date 2021/1/13 16:13
 */
public interface OpeInWhouseCombinBService {


    int updateBatch(List<OpeInWhouseCombinB> list);

    int batchInsert(List<OpeInWhouseCombinB> list);

    int insertOrUpdate(OpeInWhouseCombinB record);

    int insertOrUpdateSelective(OpeInWhouseCombinB record);

}


