package com.redescooter.ses.web.ros.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dao.base.OpeSpecificatTypeMapper;
import com.redescooter.ses.web.ros.dm.OpeSpecificatType;
import com.redescooter.ses.web.ros.service.base.OpeSpecificatTypeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OpeSpecificatTypeServiceImpl extends ServiceImpl<OpeSpecificatTypeMapper, OpeSpecificatType> implements OpeSpecificatTypeService {

    @Override
    public int updateBatch(List<OpeSpecificatType> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeSpecificatType> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeSpecificatType record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeSpecificatType record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}

