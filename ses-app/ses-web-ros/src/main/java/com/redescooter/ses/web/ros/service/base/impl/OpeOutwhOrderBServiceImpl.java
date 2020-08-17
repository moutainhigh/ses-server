package com.redescooter.ses.web.ros.service.base.impl;

import com.redescooter.ses.web.ros.dao.base.OpeOutwhOrderBMapper;
import com.redescooter.ses.web.ros.dm.OpeOutwhOrderB;
import org.springframework.stereotype.Service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.service.base.OpeOutwhOrderBService;

@Service
public class OpeOutwhOrderBServiceImpl extends ServiceImpl<OpeOutwhOrderBMapper, OpeOutwhOrderB> implements OpeOutwhOrderBService {

    @Override
    public int updateBatch(List<OpeOutwhOrderB> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeOutwhOrderB> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeOutwhOrderB record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeOutwhOrderB record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}




