package com.redescooter.ses.mobile.rps.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.redescooter.ses.mobile.rps.dao.base.OpeEntrustProductSerialNumMapper;
import com.redescooter.ses.mobile.rps.dm.OpeEntrustProductSerialNum;
import com.redescooter.ses.mobile.rps.service.base.OpeEntrustProductSerialNumService;

@Service
public class OpeEntrustProductSerialNumServiceImpl extends ServiceImpl<OpeEntrustProductSerialNumMapper, OpeEntrustProductSerialNum> implements OpeEntrustProductSerialNumService {

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








