package com.redescooter.ses.web.ros.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dao.base.OpeFactoryMapper;
import com.redescooter.ses.web.ros.dm.OpeFactory;
import com.redescooter.ses.web.ros.service.base.OpeFactoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OpeFactoryServiceImpl extends ServiceImpl<OpeFactoryMapper, OpeFactory> implements OpeFactoryService {

    @Override
    public int updateBatch(List<OpeFactory> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int batchInsert(List<OpeFactory> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(OpeFactory record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(OpeFactory record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
