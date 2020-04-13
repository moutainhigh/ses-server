package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

import com.redescooter.ses.web.ros.dao.base.OpeRepairShopUserMapper;
import com.redescooter.ses.web.ros.dm.OpeRepairShopUser;
import com.redescooter.ses.web.ros.service.base.OpeRepairShopUserService;

@Service
public class OpeRepairShopUserServiceImpl extends ServiceImpl<OpeRepairShopUserMapper, OpeRepairShopUser> implements OpeRepairShopUserService {

    @Override
    public int updateBatch(List<OpeRepairShopUser> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeRepairShopUser> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeRepairShopUser record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeRepairShopUser record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
