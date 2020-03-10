package com.redescooter.ses.web.ros.service.base.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dao.base.OpePartsSecMapper;
import com.redescooter.ses.web.ros.dm.OpePartsSec;
import com.redescooter.ses.web.ros.service.base.OpePartsSecService;
@Service
public class OpePartsSecServiceImpl extends ServiceImpl<OpePartsSecMapper, OpePartsSec> implements OpePartsSecService{

    @Override
    public int updateBatch(List<OpePartsSec> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int batchInsert(List<OpePartsSec> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(OpePartsSec record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(OpePartsSec record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
