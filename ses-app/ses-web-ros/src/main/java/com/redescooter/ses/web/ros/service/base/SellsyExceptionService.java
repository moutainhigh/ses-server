package com.redescooter.ses.web.ros.service.base;

import java.util.List;
import com.redescooter.ses.web.ros.dm.SellsyException;
import com.baomidou.mybatisplus.extension.service.IService;

public interface SellsyExceptionService extends IService<SellsyException> {

    int updateBatch(List<SellsyException> list);

    int batchInsert(List<SellsyException> list);

    int insertOrUpdate(SellsyException record);

    int insertOrUpdateSelective(SellsyException record);

}
