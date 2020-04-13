package com.redescooter.ses.web.ros.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dao.base.OpeCustomerContactMapper;
import com.redescooter.ses.web.ros.dm.OpeCustomerContact;
import com.redescooter.ses.web.ros.service.base.OpeCustomerContactService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OpeCustomerContactServiceImpl extends ServiceImpl<OpeCustomerContactMapper, OpeCustomerContact> implements OpeCustomerContactService {

    @Override
    public int updateBatch(List<OpeCustomerContact> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeCustomerContact> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeCustomerContact record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeCustomerContact record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}

