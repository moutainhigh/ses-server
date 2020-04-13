package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeStock;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional

public interface OpeStockService extends IService<OpeStock> {


    int updateBatch(List<OpeStock> list);

    int batchInsert(List<OpeStock> list);

    int insertOrUpdate(OpeStock record);

    int insertOrUpdateSelective(OpeStock record);

}


