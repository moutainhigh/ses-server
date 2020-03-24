package com.redescooter.ses.web.ros.service.base;

import com.redescooter.ses.web.ros.dm.OpeProduct;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

public interface OpeProductService extends IService<OpeProduct> {


    int updateBatch(List<OpeProduct> list);

    int batchInsert(List<OpeProduct> list);

    int insertOrUpdate(OpeProduct record);

    int insertOrUpdateSelective(OpeProduct record);

}

