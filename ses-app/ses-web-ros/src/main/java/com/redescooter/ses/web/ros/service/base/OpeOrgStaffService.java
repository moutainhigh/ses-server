package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeOrgStaff;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional

public interface OpeOrgStaffService extends IService<OpeOrgStaff> {


    int updateBatch(List<OpeOrgStaff> list);

    int batchInsert(List<OpeOrgStaff> list);

    int insertOrUpdate(OpeOrgStaff record);

    int insertOrUpdateSelective(OpeOrgStaff record);

}

