package com.redescooter.ses.mobile.rps.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.mobile.rps.dao.base.OpeProductAssemblyBMapper;
import java.util.List;
import com.redescooter.ses.mobile.rps.dm.OpeProductAssemblyB;
import com.redescooter.ses.mobile.rps.service.base.OpeProductAssemblyBService;

@Service
public class OpeProductAssemblyBServiceImpl extends ServiceImpl<OpeProductAssemblyBMapper, OpeProductAssemblyB> implements OpeProductAssemblyBService {

    @Override
    public int updateBatch(List<OpeProductAssemblyB> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeProductAssemblyB> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeProductAssemblyB record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeProductAssemblyB record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}

