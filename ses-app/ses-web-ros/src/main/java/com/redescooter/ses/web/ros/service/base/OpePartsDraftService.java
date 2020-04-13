package com.redescooter.ses.web.ros.service.base;

import java.util.List;

import com.redescooter.ses.web.ros.dm.OpePartsDraft;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OpePartsDraftService extends IService<OpePartsDraft> {


    int updateBatch(List<OpePartsDraft> list);

    int batchInsert(List<OpePartsDraft> list);

    int insertOrUpdate(OpePartsDraft record);

    int insertOrUpdateSelective(OpePartsDraft record);

}
