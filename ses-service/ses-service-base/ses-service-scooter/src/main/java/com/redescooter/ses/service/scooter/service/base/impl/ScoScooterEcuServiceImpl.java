package com.redescooter.ses.service.scooter.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.service.scooter.dao.base.ScoScooterEcuMapper;
import com.redescooter.ses.service.scooter.dm.base.ScoScooterEcu;
import com.redescooter.ses.service.scooter.service.base.ScoScooterEcuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author assert
 * @date 2020/11/20 14:32
 */
@Service
public class ScoScooterEcuServiceImpl extends ServiceImpl<ScoScooterEcuMapper, ScoScooterEcu> implements ScoScooterEcuService {

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

