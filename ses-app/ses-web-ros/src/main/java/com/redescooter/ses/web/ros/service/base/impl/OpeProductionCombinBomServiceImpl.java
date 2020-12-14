package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dm.OpeProductionCombinBom;
import java.util.List;
import com.redescooter.ses.web.ros.dao.base.OpeProductionCombinBomMapper;
import com.redescooter.ses.web.ros.service.base.OpeProductionCombinBomService;

@Service
public class OpeProductionCombinBomServiceImpl extends ServiceImpl<OpeProductionCombinBomMapper, OpeProductionCombinBom>
        implements OpeProductionCombinBomService {

    @Override
    public int updateBatch(List<OpeProductionCombinBom> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeProductionCombinBom> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeProductionCombinBom record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeProductionCombinBom record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}



