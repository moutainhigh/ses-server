package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

import com.redescooter.ses.web.ros.dao.base.OpeRepairOrderSparepartMapper;
import com.redescooter.ses.web.ros.dm.OpeRepairOrderSparepart;
import com.redescooter.ses.web.ros.service.base.OpeRepairOrderSparepartService;

@Service
public class OpeRepairOrderSparepartServiceImpl extends ServiceImpl<OpeRepairOrderSparepartMapper, OpeRepairOrderSparepart> implements OpeRepairOrderSparepartService {

    @Override
    public int updateBatch(List<OpeRepairOrderSparepart> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeRepairOrderSparepart> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeRepairOrderSparepart record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeRepairOrderSparepart record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
