package com.redescooter.ses.mobile.rps.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.mobile.rps.dm.OpeOrderSerialBind;
import java.util.List;

/**
 * @author assert
 * @date 2020/12/30 15:26
 */
public interface OpeOrderSerialBindService extends IService<OpeOrderSerialBind> {

    int updateBatch(List<OpeOrderSerialBind> list);

    int batchInsert(List<OpeOrderSerialBind> list);

    int insertOrUpdate(OpeOrderSerialBind record);

    int insertOrUpdateSelective(OpeOrderSerialBind record);

    int updateBatchSelective(List<OpeOrderSerialBind> list);
}





