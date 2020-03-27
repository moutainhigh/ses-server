package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeOrgStaffPosition;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional

public interface OpeOrgStaffPositionService extends IService<OpeOrgStaffPosition> {


    int updateBatch(List<OpeOrgStaffPosition> list);

    int batchInsert(List<OpeOrgStaffPosition> list);

    int insertOrUpdate(OpeOrgStaffPosition record);

    int insertOrUpdateSelective(OpeOrgStaffPosition record);

}
