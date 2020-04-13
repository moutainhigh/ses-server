package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dao.base.OpeRepairShopSparepartMapper;

import java.util.List;

import com.redescooter.ses.web.ros.dm.OpeRepairShopSparepart;
import com.redescooter.ses.web.ros.service.base.OpeRepairShopSparepartService;

@Service
public class OpeRepairShopSparepartServiceImpl extends ServiceImpl<OpeRepairShopSparepartMapper, OpeRepairShopSparepart> implements OpeRepairShopSparepartService {

    @Override
    public int updateBatch(List<OpeRepairShopSparepart> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeRepairShopSparepart> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeRepairShopSparepart record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeRepairShopSparepart record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
