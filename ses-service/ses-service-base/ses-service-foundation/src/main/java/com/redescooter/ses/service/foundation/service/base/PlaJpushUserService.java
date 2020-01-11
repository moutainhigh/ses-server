package com.redescooter.ses.service.foundation.service.base;

import java.util.List;

import com.redescooter.ses.service.foundation.dm.base.PlaJpushUser;
import com.baomidou.mybatisplus.extension.service.IService;

public interface PlaJpushUserService extends IService<PlaJpushUser> {


    int updateBatch(List<PlaJpushUser> list);

    int batchInsert(List<PlaJpushUser> list);

    int insertOrUpdate(PlaJpushUser record);

    int insertOrUpdateSelective(PlaJpushUser record);

    int updateBatchSelective(List<PlaJpushUser> list);
}

