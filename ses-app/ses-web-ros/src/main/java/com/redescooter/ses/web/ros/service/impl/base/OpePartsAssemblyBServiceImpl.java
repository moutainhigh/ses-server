package com.redescooter.ses.web.ros.service.impl.base;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dao.base.OpePartsAssemblyBMapper;
import com.redescooter.ses.web.ros.dm.OpePartsAssemblyB;
import com.redescooter.ses.web.ros.service.base.OpePartsAssemblyBService;

@Service
public class OpePartsAssemblyBServiceImpl extends ServiceImpl<OpePartsAssemblyBMapper, OpePartsAssemblyB> implements OpePartsAssemblyBService {

    @Override
    public int updateBatch(List<OpePartsAssemblyB> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpePartsAssemblyB> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpePartsAssemblyB record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpePartsAssemblyB record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}

