package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpePartsType;
import io.seata.spring.annotation.GlobalTransactional;

import java.util.List;

public interface OpePartsTypeService extends IService<OpePartsType>{


    int updateBatch(List<OpePartsType> list);

    int batchInsert(List<OpePartsType> list);

    int insertOrUpdate(OpePartsType record);

    int insertOrUpdateSelective(OpePartsType record);

}
