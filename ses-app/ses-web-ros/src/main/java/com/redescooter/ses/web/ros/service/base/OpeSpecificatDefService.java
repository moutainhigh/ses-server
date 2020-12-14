package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeSpecificatDef;

import java.util.List;

public interface OpeSpecificatDefService extends IService<OpeSpecificatDef> {


    int updateBatch(List<OpeSpecificatDef> list);

    int batchInsert(List<OpeSpecificatDef> list);

    int insertOrUpdate(OpeSpecificatDef record);

    int insertOrUpdateSelective(OpeSpecificatDef record);

}

