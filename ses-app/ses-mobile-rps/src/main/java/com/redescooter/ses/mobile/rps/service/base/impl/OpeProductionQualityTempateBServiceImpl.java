package com.redescooter.ses.mobile.rps.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.redescooter.ses.mobile.rps.dao.base.OpeProductionQualityTempateBMapper;
import com.redescooter.ses.mobile.rps.dm.OpeProductionQualityTempateB;
import com.redescooter.ses.mobile.rps.service.base.impl.OpeProductionQualityTempateBService;

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
