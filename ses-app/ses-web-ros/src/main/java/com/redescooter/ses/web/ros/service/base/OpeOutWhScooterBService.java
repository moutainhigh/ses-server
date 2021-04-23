package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeOutWhScooterB;

import java.util.List;

public interface OpeOutWhScooterBService extends IService<OpeOutWhScooterB> {


    int updateBatch(List<OpeOutWhScooterB> list);

    int batchInsert(List<OpeOutWhScooterB> list);

    int insertOrUpdate(OpeOutWhScooterB record);

    int insertOrUpdateSelective(OpeOutWhScooterB record);

}


