package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeProductionProduct;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional

public interface OpeProductionProductService extends IService<OpeProductionProduct> {


    int updateBatch(List<OpeProductionProduct> list);

    int batchInsert(List<OpeProductionProduct> list);

    int insertOrUpdate(OpeProductionProduct record);

    int insertOrUpdateSelective(OpeProductionProduct record);

}
