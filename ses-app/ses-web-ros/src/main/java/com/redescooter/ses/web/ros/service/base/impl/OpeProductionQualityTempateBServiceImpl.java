package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dm.OpeProductionQualityTempateB;
import com.redescooter.ses.web.ros.dao.base.OpeProductionQualityTempateBMapper;
import com.redescooter.ses.web.ros.service.base.OpeProductionQualityTempateBService;

@Service
public class OpeProductionQualityTempateBServiceImpl
    extends ServiceImpl<OpeProductionQualityTempateBMapper, OpeProductionQualityTempateB>
    implements OpeProductionQualityTempateBService {

    @Override
    public int updateBatch(List<OpeProductionQualityTempateB> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeProductionQualityTempateB> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeProductionQualityTempateB record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeProductionQualityTempateB record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
