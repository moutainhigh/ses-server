package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.redescooter.ses.web.ros.dm.OpeAssemblyOrderPart;
import com.redescooter.ses.web.ros.dao.base.OpeAssemblyOrderPartMapper;
import com.redescooter.ses.web.ros.service.base.OpeAssemblyOrderPartService;

@Service
public class OpeAssemblyOrderPartServiceImpl extends ServiceImpl<OpeAssemblyOrderPartMapper, OpeAssemblyOrderPart> implements OpeAssemblyOrderPartService {

    @Override
    public int updateBatch(List<OpeAssemblyOrderPart> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeAssemblyOrderPart> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeAssemblyOrderPart record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeAssemblyOrderPart record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}

