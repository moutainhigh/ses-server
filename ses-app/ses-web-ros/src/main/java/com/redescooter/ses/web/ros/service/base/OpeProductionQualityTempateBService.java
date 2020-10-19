package com.redescooter.ses.web.ros.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.web.ros.dm.OpeProductionQualityTempateB;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface OpeProductionQualityTempateBService extends IService<OpeProductionQualityTempateB> {

    int updateBatch(List<OpeProductionQualityTempateB> list);

    int batchInsert(List<OpeProductionQualityTempateB> list);

    int insertOrUpdate(OpeProductionQualityTempateB record);

    int insertOrUpdateSelective(OpeProductionQualityTempateB record);

}
