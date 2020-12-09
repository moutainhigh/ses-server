package com.redescooter.ses.service.scooter.service.base.impl;

import com.redescooter.ses.service.scooter.dm.base.ScoScooterBms;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import com.redescooter.ses.service.scooter.dao.base.ScoScooterBmsMapper;
import com.redescooter.ses.service.scooter.service.base.ScoScooterBmsService;

/**
 * @author assert
 * @date 2020/11/20 14:26
 */
@Service
public class ScoScooterBmsServiceImpl implements ScoScooterBmsService {

    @Resource
    private ScoScooterBmsMapper scoScooterBmsMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return scoScooterBmsMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(ScoScooterBms record) {
        return scoScooterBmsMapper.insert(record);
    }

    @Override
    public int insertSelective(ScoScooterBms record) {
        return scoScooterBmsMapper.insertSelective(record);
    }

    @Override
    public ScoScooterBms selectByPrimaryKey(Long id) {
        return scoScooterBmsMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(ScoScooterBms record) {
        return scoScooterBmsMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(ScoScooterBms record) {
        return scoScooterBmsMapper.updateByPrimaryKey(record);
    }

}

