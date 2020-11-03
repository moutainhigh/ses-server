package com.redescooter.ses.mobile.rps.service.base;

import java.util.List;
import com.redescooter.ses.mobile.rps.dm.OpeOrderSerialBind;
import com.baomidou.mybatisplus.extension.service.IService;
public interface OpeOrderSerialBindService extends IService<OpeOrderSerialBind>{


    int updateBatch(List<OpeOrderSerialBind> list);

    int batchInsert(List<OpeOrderSerialBind> list);

    int insertOrUpdate(OpeOrderSerialBind record);

    int insertOrUpdateSelective(OpeOrderSerialBind record);

}
