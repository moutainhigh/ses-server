package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeWarehouse;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional

public interface OpeWarehouseService extends IService<OpeWarehouse> {


    int updateBatch(List<OpeWarehouse> list);

    int batchInsert(List<OpeWarehouse> list);

    int insertOrUpdate(OpeWarehouse record);

    int insertOrUpdateSelective(OpeWarehouse record);

}
