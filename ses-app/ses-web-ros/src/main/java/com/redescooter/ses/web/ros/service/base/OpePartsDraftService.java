package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpePartsDraft;
import io.seata.spring.annotation.GlobalTransactional;

import java.util.List;

public interface OpePartsDraftService extends IService<OpePartsDraft> {


    int updateBatch(List<OpePartsDraft> list);

    int batchInsert(List<OpePartsDraft> list);

    int insertOrUpdate(OpePartsDraft record);

    int insertOrUpdateSelective(OpePartsDraft record);

}

