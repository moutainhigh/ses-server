package com.redescooter.ses.service.foundation.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.service.foundation.dm.base.PlaUserNode;

import java.util.List;

public interface PlaUserNodeService extends IService<PlaUserNode> {


    int updateBatch(List<PlaUserNode> list);

    int batchInsert(List<PlaUserNode> list);

    int insertOrUpdate(PlaUserNode record);

    int insertOrUpdateSelective(PlaUserNode record);

}
