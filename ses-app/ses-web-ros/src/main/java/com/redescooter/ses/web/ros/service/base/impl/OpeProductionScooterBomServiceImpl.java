package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.redescooter.ses.web.ros.dao.base.OpeProductionScooterBomMapper;
import com.redescooter.ses.web.ros.dm.OpeProductionScooterBom;
import com.redescooter.ses.web.ros.service.base.OpeProductionScooterBomService;

@Service
public class OpeProductionScooterBomServiceImpl extends
    ServiceImpl<OpeProductionScooterBomMapper, OpeProductionScooterBom> implements OpeProductionScooterBomService {

    @Override
    public int updateBatch(List<OpeProductionScooterBom> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeProductionScooterBom> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeProductionScooterBom record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeProductionScooterBom record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}


