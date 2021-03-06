package com.redescooter.ses.service.foundation.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.service.foundation.dm.base.PlaUserPassword;

import java.util.List;

public interface PlaUserPasswordService extends IService<PlaUserPassword> {


    int updateBatch(List<PlaUserPassword> list);

    int batchInsert(List<PlaUserPassword> list);

    int insertOrUpdate(PlaUserPassword record);

    int insertOrUpdateSelective(PlaUserPassword record);

}
