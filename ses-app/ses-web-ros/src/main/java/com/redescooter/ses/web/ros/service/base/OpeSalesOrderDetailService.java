package com.redescooter.ses.web.ros.service.base;

import java.util.List;

import com.redescooter.ses.web.ros.dm.OpeSalesOrderDetail;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OpeSalesOrderDetailService extends IService<OpeSalesOrderDetail> {


    int updateBatch(List<OpeSalesOrderDetail> list);

    int batchInsert(List<OpeSalesOrderDetail> list);

    int insertOrUpdate(OpeSalesOrderDetail record);

    int insertOrUpdateSelective(OpeSalesOrderDetail record);

}
