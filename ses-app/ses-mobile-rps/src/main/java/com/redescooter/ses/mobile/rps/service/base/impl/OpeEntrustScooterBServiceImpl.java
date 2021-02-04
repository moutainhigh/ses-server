package com.redescooter.ses.mobile.rps.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.mobile.rps.dm.OpeEntrustScooterB;
import java.util.List;
import com.redescooter.ses.mobile.rps.dao.base.OpeEntrustScooterBMapper;
import com.redescooter.ses.mobile.rps.service.base.OpeEntrustScooterBService;

@Service
public class OpeEntrustScooterBServiceImpl extends ServiceImpl<OpeEntrustScooterBMapper, OpeEntrustScooterB> implements OpeEntrustScooterBService {

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

