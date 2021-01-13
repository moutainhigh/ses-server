package com.redescooter.ses.mobile.rps.service.base;

import java.util.List;
import com.redescooter.ses.mobile.rps.dm.OpeInWhousePartsB;
    /**
 *@author assert
 *@date 2021/1/13 16:13
 */
public interface OpeInWhousePartsBService{


    int updateBatch(List<OpeInWhousePartsB> list);

    int batchInsert(List<OpeInWhousePartsB> list);

    int insertOrUpdate(OpeInWhousePartsB record);

    int insertOrUpdateSelective(OpeInWhousePartsB record);

}
