package com.redescooter.ses.web.ros.service.impl.base;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dao.base.OpePartsProductBMapper;
import com.redescooter.ses.web.ros.dm.OpePartsProductB;
import com.redescooter.ses.web.ros.service.base.OpePartsProductBService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OpePartsProductBServiceImpl extends ServiceImpl<OpePartsProductBMapper, OpePartsProductB> implements OpePartsProductBService {

    @Override
    public int updateBatch(List<OpePartsProductB> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpePartsProductB> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpePartsProductB record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpePartsProductB record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}


