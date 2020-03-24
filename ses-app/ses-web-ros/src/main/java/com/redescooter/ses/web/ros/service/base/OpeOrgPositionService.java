package com.redescooter.ses.web.ros.service.base;

import com.redescooter.ses.web.ros.dm.OpeOrgPosition;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

public interface OpeOrgPositionService extends IService<OpeOrgPosition> {


    int updateBatch(List<OpeOrgPosition> list);

    int batchInsert(List<OpeOrgPosition> list);

    int insertOrUpdate(OpeOrgPosition record);

    int insertOrUpdateSelective(OpeOrgPosition record);

}
