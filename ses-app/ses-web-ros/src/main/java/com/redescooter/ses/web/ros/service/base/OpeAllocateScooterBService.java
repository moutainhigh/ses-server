package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeAllocateScooterB;

import java.util.List;

public interface OpeAllocateScooterBService extends IService<OpeAllocateScooterB> {


    int updateBatch(List<OpeAllocateScooterB> list);

    int batchInsert(List<OpeAllocateScooterB> list);

    int insertOrUpdate(OpeAllocateScooterB record);

    int insertOrUpdateSelective(OpeAllocateScooterB record);

}
