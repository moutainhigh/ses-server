package com.redescooter.ses.web.ros.service.base;

import java.util.List;

import com.redescooter.ses.web.ros.dm.OpeOrgDepartment;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OpeOrgDepartmentService extends IService<OpeOrgDepartment> {


    int updateBatch(List<OpeOrgDepartment> list);

    int batchInsert(List<OpeOrgDepartment> list);

    int insertOrUpdate(OpeOrgDepartment record);

    int insertOrUpdateSelective(OpeOrgDepartment record);

}
