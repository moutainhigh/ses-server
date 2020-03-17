package com.redescooter.ses.service.foundation.service.base;

import java.util.List;

import com.redescooter.ses.service.foundation.dm.base.PlaUser;
import com.baomidou.mybatisplus.extension.service.IService;

public interface PlaUserService extends IService<PlaUser> {


    int updateBatch(List<PlaUser> list);

    int batchInsert(List<PlaUser> list);

    int insertOrUpdate(PlaUser record);

    int insertOrUpdateSelective(PlaUser record);

}





