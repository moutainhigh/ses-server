package com.redescooter.ses.service.foundation.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.service.foundation.dm.base.PlaUser;

import java.util.List;

public interface PlaUserService extends IService<PlaUser> {


    int updateBatch(List<PlaUser> list);

    int batchInsert(List<PlaUser> list);

    int insertOrUpdate(PlaUser record);

    int insertOrUpdateSelective(PlaUser record);

}





