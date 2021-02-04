package com.redescooter.ses.mobile.rps.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.mobile.rps.dm.OpeQcOrderSerialBind;
import java.util.List;

/**
 * @author assert
 * @date 2021/1/25 10:02
 */
public interface OpeQcOrderSerialBindService extends IService<OpeQcOrderSerialBind> {

    int updateBatch(List<OpeQcOrderSerialBind> list);

    int updateBatchSelective(List<OpeQcOrderSerialBind> list);

    int batchInsert(List<OpeQcOrderSerialBind> list);

    int insertOrUpdate(OpeQcOrderSerialBind record);

    int insertOrUpdateSelective(OpeQcOrderSerialBind record);
}




