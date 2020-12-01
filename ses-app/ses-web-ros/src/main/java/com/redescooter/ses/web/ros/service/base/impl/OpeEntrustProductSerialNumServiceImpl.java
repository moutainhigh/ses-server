package com.redescooter.ses.web.ros.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dao.base.OpeEntrustProductSerialNumMapper;
import com.redescooter.ses.web.ros.dm.OpeEntrustProductSerialNum;
import com.redescooter.ses.web.ros.service.base.OpeEntrustProductSerialNumService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OpeEntrustProductSerialNumServiceImpl extends ServiceImpl<OpeEntrustProductSerialNumMapper, OpeEntrustProductSerialNum> implements OpeEntrustProductSerialNumService{

    @Override
    public int updateBatch(List<OpeEntrustProductSerialNum> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int batchInsert(List<OpeEntrustProductSerialNum> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(OpeEntrustProductSerialNum record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(OpeEntrustProductSerialNum record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
