package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.redescooter.ses.web.ros.dao.base.OpeProductionBomMapper;
import com.redescooter.ses.web.ros.dm.OpeProductionBom;
import com.redescooter.ses.web.ros.service.base.OpeProductionBomService;

@Service
public class OpeProductionBomServiceImpl extends ServiceImpl<OpeProductionBomMapper, OpeProductionBom>
    implements OpeProductionBomService {

    @Override
    public int updateBatch(List<OpeProductionBom> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeProductionBom> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeProductionBom record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeProductionBom record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
