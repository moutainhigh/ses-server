package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeSalesOrderDetail;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional

public interface OpeSalesOrderDetailService extends IService<OpeSalesOrderDetail> {


    int updateBatch(List<OpeSalesOrderDetail> list);

    int batchInsert(List<OpeSalesOrderDetail> list);

    int insertOrUpdate(OpeSalesOrderDetail record);

    int insertOrUpdateSelective(OpeSalesOrderDetail record);

}
