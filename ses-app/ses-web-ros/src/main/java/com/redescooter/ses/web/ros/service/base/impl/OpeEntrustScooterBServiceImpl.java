package com.redescooter.ses.web.ros.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dao.base.OpeEntrustScooterBMapper;
import com.redescooter.ses.web.ros.dm.OpeEntrustScooterB;
import com.redescooter.ses.web.ros.service.base.OpeEntrustScooterBService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OpeEntrustScooterBServiceImpl extends ServiceImpl<OpeEntrustScooterBMapper, OpeEntrustScooterB> implements OpeEntrustScooterBService{

    @Override
    public int updateBatch(List<OpeEntrustScooterB> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int batchInsert(List<OpeEntrustScooterB> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(OpeEntrustScooterB record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(OpeEntrustScooterB record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
