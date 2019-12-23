package com.redescooter.ses.service.hub.service.corporate.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.redescooter.ses.service.hub.dao.corporate.base.CorDriverMapper;
import com.redescooter.ses.service.hub.dm.corporate.base.CorDriver;
import com.redescooter.ses.service.hub.service.corporate.base.CorDriverService;
@Service
public class CorDriverServiceImpl implements CorDriverService{

    @Resource
    private CorDriverMapper corDriverMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return corDriverMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(CorDriver record) {
        return corDriverMapper.insert(record);
    }

    @Override
    public int insertOrUpdate(CorDriver record) {
        return corDriverMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(CorDriver record) {
        return corDriverMapper.insertOrUpdateSelective(record);
    }

    @Override
    public int insertSelective(CorDriver record) {
        return corDriverMapper.insertSelective(record);
    }

    @Override
    public CorDriver selectByPrimaryKey(Long id) {
        return corDriverMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(CorDriver record) {
        return corDriverMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(CorDriver record) {
        return corDriverMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateBatch(List<CorDriver> list) {
        return corDriverMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<CorDriver> list) {
        return corDriverMapper.batchInsert(list);
    }

}
