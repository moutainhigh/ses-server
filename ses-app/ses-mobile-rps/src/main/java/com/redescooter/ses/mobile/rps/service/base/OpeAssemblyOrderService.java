package com.redescooter.ses.mobile.rps.service.base;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

public interface OpeAssemblyOrderService extends IService<OpeAssemblyOrder> {


    int updateBatch(List<OpeAssemblyOrder> list);

    int batchInsert(List<OpeAssemblyOrder> list);

    int insertOrUpdate(OpeAssemblyOrder record);

    int insertOrUpdateSelective(OpeAssemblyOrder record);

}



