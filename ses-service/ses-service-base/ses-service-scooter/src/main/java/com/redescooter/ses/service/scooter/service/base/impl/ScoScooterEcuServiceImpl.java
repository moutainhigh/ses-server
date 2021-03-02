package com.redescooter.ses.service.scooter.service.base.impl;

import com.redescooter.ses.service.scooter.dm.base.ScoScooterEcu;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import com.redescooter.ses.service.scooter.dao.base.ScoScooterEcuMapper;
import com.redescooter.ses.service.scooter.service.base.ScoScooterEcuService;

/**
 * @author assert
 * @date 2020/11/20 14:32
 */
@Service
public class ScoScooterEcuServiceImpl implements ScoScooterEcuService {

    @Resource
    private ScoScooterEcuMapper scoScooterEcuMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return scoScooterEcuMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(ScoScooterEcu record) {
        return scoScooterEcuMapper.insert(record);
    }

    @Override
    public int insertSelective(ScoScooterEcu record) {
        return scoScooterEcuMapper.insertSelective(record);
    }

    @Override
    public ScoScooterEcu selectByPrimaryKey(Long id) {
        return scoScooterEcuMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(ScoScooterEcu record) {
        return scoScooterEcuMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(ScoScooterEcu record) {
        return scoScooterEcuMapper.updateByPrimaryKey(record);
    }

}

