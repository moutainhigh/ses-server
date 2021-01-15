package com.redescooter.ses.mobile.rps.service.base;

import com.redescooter.ses.mobile.rps.dm.OpeInWhouseScooterB;
import java.util.List;

/**
 * @author assert
 * @date 2021/1/13 15:53
 */
public interface OpeInWhouseScooterBService {

    int updateBatch(List<OpeInWhouseScooterB> list);

    int batchInsert(List<OpeInWhouseScooterB> list);

    int insertOrUpdate(OpeInWhouseScooterB record);

    int insertOrUpdateSelective(OpeInWhouseScooterB record);
}




