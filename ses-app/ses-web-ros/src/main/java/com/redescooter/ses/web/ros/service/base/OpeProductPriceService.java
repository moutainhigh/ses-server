package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeProductPrice;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional

public interface OpeProductPriceService extends IService<OpeProductPrice> {


    int updateBatch(List<OpeProductPrice> list);

    int batchInsert(List<OpeProductPrice> list);

    int insertOrUpdate(OpeProductPrice record);

    int insertOrUpdateSelective(OpeProductPrice record);

}

