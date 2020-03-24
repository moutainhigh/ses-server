package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

import com.redescooter.ses.web.ros.dao.base.OpeSysRoleSalesCidyMapper;
import com.redescooter.ses.web.ros.dm.OpeSysRoleSalesCidy;
import com.redescooter.ses.web.ros.service.base.OpeSysRoleSalesCidyService;

@Service
public class OpeSysRoleSalesCidyServiceImpl extends ServiceImpl<OpeSysRoleSalesCidyMapper, OpeSysRoleSalesCidy> implements OpeSysRoleSalesCidyService {

    @Override
    public int batchInsert(List<OpeSysRoleSalesCidy> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeSysRoleSalesCidy record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeSysRoleSalesCidy record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}

