package com.redescooter.ses.web.ros.service.base;

import java.util.List;
import com.redescooter.ses.web.ros.dm.OpeInWhouseScooterB;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OpeInWhouseScooterBService extends IService<OpeInWhouseScooterB> {


    int updateBatch(List<OpeInWhouseScooterB> list);

    int batchInsert(List<OpeInWhouseScooterB> list);

    int insertOrUpdate(OpeInWhouseScooterB record);

    int insertOrUpdateSelective(OpeInWhouseScooterB record);

}

