package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeProductionQualityTempate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface OpeProductionQualityTempateService extends IService<OpeProductionQualityTempate> {

    int updateBatch(List<OpeProductionQualityTempate> list);

    int batchInsert(List<OpeProductionQualityTempate> list);

    int insertOrUpdate(OpeProductionQualityTempate record);

    int insertOrUpdateSelective(OpeProductionQualityTempate record);

}

