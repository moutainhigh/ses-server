package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeProductType;

import java.util.List;

public interface OpeProductTypeService extends IService<OpeProductType> {


    int updateBatch(List<OpeProductType> list);

    int batchInsert(List<OpeProductType> list);

    int insertOrUpdate(OpeProductType record);

    int insertOrUpdateSelective(OpeProductType record);

}

