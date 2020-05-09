package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.PdmanDbVersion;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional

public interface PdmanDbVersionService extends IService<PdmanDbVersion> {


    int batchInsert(List<PdmanDbVersion> list);

    int insertOrUpdate(PdmanDbVersion record);

    int insertOrUpdateSelective(PdmanDbVersion record);

}
