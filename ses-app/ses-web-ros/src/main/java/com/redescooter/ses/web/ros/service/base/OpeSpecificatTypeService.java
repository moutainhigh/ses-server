package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeSpecificatType;

import java.util.List;

public interface OpeSpecificatTypeService extends IService<OpeSpecificatType> {


    int updateBatch(List<OpeSpecificatType> list);

    int batchInsert(List<OpeSpecificatType> list);

    int insertOrUpdate(OpeSpecificatType record);

    int insertOrUpdateSelective(OpeSpecificatType record);

}

