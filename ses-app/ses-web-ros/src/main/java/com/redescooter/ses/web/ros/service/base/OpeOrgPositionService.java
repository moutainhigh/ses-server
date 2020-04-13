package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeOrgPosition;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional

public interface OpeOrgPositionService extends IService<OpeOrgPosition> {


    int updateBatch(List<OpeOrgPosition> list);

    int batchInsert(List<OpeOrgPosition> list);

    int insertOrUpdate(OpeOrgPosition record);

    int insertOrUpdateSelective(OpeOrgPosition record);

}
