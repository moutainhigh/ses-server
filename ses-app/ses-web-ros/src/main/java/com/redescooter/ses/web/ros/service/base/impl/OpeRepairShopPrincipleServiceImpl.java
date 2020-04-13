package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

import com.redescooter.ses.web.ros.dao.base.OpeRepairShopPrincipleMapper;
import com.redescooter.ses.web.ros.dm.OpeRepairShopPrinciple;
import com.redescooter.ses.web.ros.service.base.OpeRepairShopPrincipleService;

@Service
public class OpeRepairShopPrincipleServiceImpl extends ServiceImpl<OpeRepairShopPrincipleMapper, OpeRepairShopPrinciple> implements OpeRepairShopPrincipleService {

    @Override
    public int updateBatch(List<OpeRepairShopPrinciple> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeRepairShopPrinciple> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeRepairShopPrinciple record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeRepairShopPrinciple record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
