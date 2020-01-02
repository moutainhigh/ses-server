package com.redescooter.ses.service.hub.source.consumer.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.redescooter.ses.service.hub.source.consumer.dm.ConUserScooter;
import com.redescooter.ses.service.hub.source.consumer.dao.ConUserScooterMapper;
import com.redescooter.ses.service.hub.source.consumer.service.base.ConUserScooterService;

@Service
public class ConUserScooterServiceImpl implements ConUserScooterService {

    @Resource
    private ConUserScooterMapper conUserScooterMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return conUserScooterMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(ConUserScooter record) {
        return conUserScooterMapper.insert(record);
    }

    @Override
    public int insertOrUpdate(ConUserScooter record) {
        return conUserScooterMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(ConUserScooter record) {
        return conUserScooterMapper.insertOrUpdateSelective(record);
    }

    @Override
    public int insertSelective(ConUserScooter record) {
        return conUserScooterMapper.insertSelective(record);
    }

    @Override
    public ConUserScooter selectByPrimaryKey(Long id) {
        return conUserScooterMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(ConUserScooter record) {
        return conUserScooterMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(ConUserScooter record) {
        return conUserScooterMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateBatch(List<ConUserScooter> list) {
        return conUserScooterMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<ConUserScooter> list) {
        return conUserScooterMapper.batchInsert(list);
    }

}

