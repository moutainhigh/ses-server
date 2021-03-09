package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeAllocatePartsB;

import java.util.List;

public interface OpeAllocatePartsBService extends IService<OpeAllocatePartsB> {


    int updateBatch(List<OpeAllocatePartsB> list);

    int batchInsert(List<OpeAllocatePartsB> list);

    int insertOrUpdate(OpeAllocatePartsB record);

    int insertOrUpdateSelective(OpeAllocatePartsB record);

}
