package com.redescooter.ses.mobile.rps.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.redescooter.ses.mobile.rps.dao.base.OpeEntrustCombinBMapper;
import com.redescooter.ses.mobile.rps.dm.OpeEntrustCombinB;
import com.redescooter.ses.mobile.rps.service.base.OpeEntrustCombinBService;

@Service
public class OpeEntrustCombinBServiceImpl extends ServiceImpl<OpeEntrustCombinBMapper, OpeEntrustCombinB> implements OpeEntrustCombinBService {

    @Override
    public int updateBatch(List<OpeEntrustCombinB> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeEntrustCombinB> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeEntrustCombinB record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeEntrustCombinB record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}

