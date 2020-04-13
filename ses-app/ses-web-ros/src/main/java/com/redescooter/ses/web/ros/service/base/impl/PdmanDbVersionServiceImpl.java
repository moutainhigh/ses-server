package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

import com.redescooter.ses.web.ros.dao.base.PdmanDbVersionMapper;
import com.redescooter.ses.web.ros.dm.PdmanDbVersion;
import com.redescooter.ses.web.ros.service.base.PdmanDbVersionService;

@Service
public class PdmanDbVersionServiceImpl extends ServiceImpl<PdmanDbVersionMapper, PdmanDbVersion> implements PdmanDbVersionService {

    @Override
    public int batchInsert(List<PdmanDbVersion> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(PdmanDbVersion record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(PdmanDbVersion record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
