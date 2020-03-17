package com.redescooter.ses.service.foundation.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.service.foundation.dm.base.PlaUserNode;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface PlaUserNodeService extends IService<PlaUserNode> {


    int updateBatch(List<PlaUserNode> list);

    int batchInsert(List<PlaUserNode> list);

    int insertOrUpdate(PlaUserNode record);

    int insertOrUpdateSelective(PlaUserNode record);

}
