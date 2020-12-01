package com.redescooter.ses.web.ros.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dao.base.OpeEntrustCombinBMapper;
import com.redescooter.ses.web.ros.dm.OpeEntrustCombinB;
import com.redescooter.ses.web.ros.service.base.OpeEntrustCombinBService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OpeEntrustCombinBServiceImpl extends ServiceImpl<OpeEntrustCombinBMapper, OpeEntrustCombinB> implements OpeEntrustCombinBService{

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
