package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeProduct;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional

public interface OpeProductService extends IService<OpeProduct> {


    int updateBatch(List<OpeProduct> list);

    int batchInsert(List<OpeProduct> list);

    int insertOrUpdate(OpeProduct record);

    int insertOrUpdateSelective(OpeProduct record);

}

