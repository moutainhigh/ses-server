package com.redescooter.ses.web.ros.service.base;

import java.util.List;

import com.redescooter.ses.web.ros.dm.PdmanDbVersion;
import com.baomidou.mybatisplus.extension.service.IService;

public interface PdmanDbVersionService extends IService<PdmanDbVersion> {


    int batchInsert(List<PdmanDbVersion> list);

    int insertOrUpdate(PdmanDbVersion record);

    int insertOrUpdateSelective(PdmanDbVersion record);

}
