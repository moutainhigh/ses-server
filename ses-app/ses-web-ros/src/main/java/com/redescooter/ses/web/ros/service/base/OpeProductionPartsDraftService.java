package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeProductionPartsDraft;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface OpeProductionPartsDraftService extends IService<OpeProductionPartsDraft> {

    int updateBatch(List<OpeProductionPartsDraft> list);

    int batchInsert(List<OpeProductionPartsDraft> list);

    int insertOrUpdate(OpeProductionPartsDraft record);

    int insertOrUpdateSelective(OpeProductionPartsDraft record);

}

