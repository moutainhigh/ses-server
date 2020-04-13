package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

import com.redescooter.ses.web.ros.dao.base.OpeRepairShopMapper;
import com.redescooter.ses.web.ros.dm.OpeRepairShop;
import com.redescooter.ses.web.ros.service.base.OpeRepairShopService;

@Service
public class OpeRepairShopServiceImpl extends ServiceImpl<OpeRepairShopMapper, OpeRepairShop> implements OpeRepairShopService {

    @Override
    public int updateBatch(List<OpeRepairShop> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeRepairShop> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeRepairShop record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeRepairShop record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
