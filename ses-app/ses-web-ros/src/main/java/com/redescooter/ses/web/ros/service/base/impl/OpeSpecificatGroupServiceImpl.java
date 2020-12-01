package com.redescooter.ses.web.ros.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dao.base.OpeSpecificatGroupMapper;
import com.redescooter.ses.web.ros.dm.OpeSpecificatGroup;
import com.redescooter.ses.web.ros.service.base.OpeSpecificatGroupService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OpeSpecificatGroupServiceImpl extends ServiceImpl<OpeSpecificatGroupMapper, OpeSpecificatGroup> implements OpeSpecificatGroupService{

    @Override
    public int updateBatch(List<OpeSpecificatGroup> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int updateBatchSelective(List<OpeSpecificatGroup> list) {
        return baseMapper.updateBatchSelective(list);
    }
    @Override
    public int batchInsert(List<OpeSpecificatGroup> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(OpeSpecificatGroup record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(OpeSpecificatGroup record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
