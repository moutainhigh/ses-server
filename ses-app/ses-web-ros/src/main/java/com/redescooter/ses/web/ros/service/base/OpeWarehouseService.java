package com.redescooter.ses.web.ros.service.base;

import java.util.List;

import com.redescooter.ses.web.ros.dm.OpeWarehouse;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OpeWarehouseService extends IService<OpeWarehouse> {


    int updateBatch(List<OpeWarehouse> list);

    int batchInsert(List<OpeWarehouse> list);

    int insertOrUpdate(OpeWarehouse record);

    int insertOrUpdateSelective(OpeWarehouse record);

}
