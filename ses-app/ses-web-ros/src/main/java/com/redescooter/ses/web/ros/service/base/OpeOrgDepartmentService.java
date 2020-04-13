package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeOrgDepartment;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional

public interface OpeOrgDepartmentService extends IService<OpeOrgDepartment> {


    int updateBatch(List<OpeOrgDepartment> list);

    int batchInsert(List<OpeOrgDepartment> list);

    int insertOrUpdate(OpeOrgDepartment record);

    int insertOrUpdateSelective(OpeOrgDepartment record);

}
