package com.redescooter.ses.service.foundation.service.base.impl;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.service.foundation.dm.base.PlaUserNode;

import java.util.List;

import com.redescooter.ses.service.foundation.dao.base.PlaUserNodeMapper;
import com.redescooter.ses.service.foundation.service.base.PlaUserNodeService;

@Service
public class PlaUserNodeServiceImpl extends ServiceImpl<PlaUserNodeMapper, PlaUserNode> implements PlaUserNodeService {

    @Override
    public int updateBatch(List<PlaUserNode> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<PlaUserNode> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(PlaUserNode record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(PlaUserNode record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
