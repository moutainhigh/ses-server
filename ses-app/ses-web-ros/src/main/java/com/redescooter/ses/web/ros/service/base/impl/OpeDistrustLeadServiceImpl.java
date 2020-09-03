package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dm.OpeDistrustLead;
import com.redescooter.ses.web.ros.dao.base.OpeDistrustLeadMapper;
import com.redescooter.ses.web.ros.service.base.OpeDistrustLeadService;
@Service
public class OpeDistrustLeadServiceImpl extends ServiceImpl<OpeDistrustLeadMapper, OpeDistrustLead> implements OpeDistrustLeadService{

    @Override
    public int updateBatch(List<OpeDistrustLead> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int updateBatchSelective(List<OpeDistrustLead> list) {
        return baseMapper.updateBatchSelective(list);
    }
    @Override
    public int batchInsert(List<OpeDistrustLead> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(OpeDistrustLead record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(OpeDistrustLead record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
