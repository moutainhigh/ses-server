package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.redescooter.ses.web.ros.dm.OpeAssemblyBQc;
import com.redescooter.ses.web.ros.dao.base.OpeAssemblyBQcMapper;
import com.redescooter.ses.web.ros.service.base.OpeAssemblyBQcService;
@Service
public class OpeAssemblyBQcServiceImpl extends ServiceImpl<OpeAssemblyBQcMapper, OpeAssemblyBQc> implements OpeAssemblyBQcService{

    @Override
    public int updateBatch(List<OpeAssemblyBQc> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int batchInsert(List<OpeAssemblyBQc> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(OpeAssemblyBQc record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(OpeAssemblyBQc record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
