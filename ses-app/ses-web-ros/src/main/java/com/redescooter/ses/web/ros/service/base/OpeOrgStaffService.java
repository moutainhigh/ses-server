package com.redescooter.ses.web.ros.service.base;

import com.redescooter.ses.web.ros.dm.OpeOrgStaff;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

public interface OpeOrgStaffService extends IService<OpeOrgStaff> {


    int updateBatch(List<OpeOrgStaff> list);

    int batchInsert(List<OpeOrgStaff> list);

    int insertOrUpdate(OpeOrgStaff record);

    int insertOrUpdateSelective(OpeOrgStaff record);

}

