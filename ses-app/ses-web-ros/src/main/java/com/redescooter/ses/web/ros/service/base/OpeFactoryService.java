package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeFactory;

import java.util.List;

public interface OpeFactoryService extends IService<OpeFactory> {


    int updateBatch(List<OpeFactory> list);

    int batchInsert(List<OpeFactory> list);

    int insertOrUpdate(OpeFactory record);

    int insertOrUpdateSelective(OpeFactory record);

}
