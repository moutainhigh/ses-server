package com.redescooter.ses.web.ros.service.impl.base;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dao.base.OpePartsAssemblyMapper;
import com.redescooter.ses.web.ros.dm.OpePartsAssembly;
import com.redescooter.ses.web.ros.service.base.OpePartsAssemblyService;
@Service
public class OpePartsAssemblyServiceImpl extends ServiceImpl<OpePartsAssemblyMapper, OpePartsAssembly> implements OpePartsAssemblyService{

    @Override
    public int updateBatch(List<OpePartsAssembly> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int batchInsert(List<OpePartsAssembly> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(OpePartsAssembly record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(OpePartsAssembly record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
