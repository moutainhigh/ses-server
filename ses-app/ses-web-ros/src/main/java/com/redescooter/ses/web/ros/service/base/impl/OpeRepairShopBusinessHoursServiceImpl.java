package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

import com.redescooter.ses.web.ros.dm.OpeRepairShopBusinessHours;
import com.redescooter.ses.web.ros.dao.base.OpeRepairShopBusinessHoursMapper;
import com.redescooter.ses.web.ros.service.base.OpeRepairShopBusinessHoursService;

@Service
public class OpeRepairShopBusinessHoursServiceImpl extends ServiceImpl<OpeRepairShopBusinessHoursMapper, OpeRepairShopBusinessHours> implements OpeRepairShopBusinessHoursService {

    @Override
    public int updateBatch(List<OpeRepairShopBusinessHours> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeRepairShopBusinessHours> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeRepairShopBusinessHours record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeRepairShopBusinessHours record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
