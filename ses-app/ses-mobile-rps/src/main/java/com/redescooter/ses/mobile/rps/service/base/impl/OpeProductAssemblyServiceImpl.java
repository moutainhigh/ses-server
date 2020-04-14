package com.redescooter.ses.mobile.rps.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.mobile.rps.dm.OpeProductAssembly;
import java.util.List;
import com.redescooter.ses.mobile.rps.dao.base.OpeProductAssemblyMapper;
import com.redescooter.ses.mobile.rps.service.base.OpeProductAssemblyService;

@Service
public class OpeProductAssemblyServiceImpl extends ServiceImpl<OpeProductAssemblyMapper, OpeProductAssembly> implements OpeProductAssemblyService {

    @Override
    public int updateBatch(List<OpeProductAssembly> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeProductAssembly> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeProductAssembly record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeProductAssembly record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}

