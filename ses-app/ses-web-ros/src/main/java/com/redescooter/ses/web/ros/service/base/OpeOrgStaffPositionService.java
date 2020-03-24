package com.redescooter.ses.web.ros.service.base;

import java.util.List;

import com.redescooter.ses.web.ros.dm.OpeOrgStaffPosition;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OpeOrgStaffPositionService extends IService<OpeOrgStaffPosition> {


    int updateBatch(List<OpeOrgStaffPosition> list);

    int batchInsert(List<OpeOrgStaffPosition> list);

    int insertOrUpdate(OpeOrgStaffPosition record);

    int insertOrUpdateSelective(OpeOrgStaffPosition record);

}
