package com.redescooter.ses.service.scooter.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.service.scooter.dao.base.ScoScooterMcuMapper;
import com.redescooter.ses.service.scooter.dm.base.ScoScooterMcu;
import com.redescooter.ses.service.scooter.service.base.ScoScooterMcuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author assert
 * @date 2020/11/20 14:32
 */
@Slf4j
@Service
public class ScoScooterMcuServiceImpl extends ServiceImpl<ScoScooterMcuMapper, ScoScooterMcu> implements ScoScooterMcuService{

    @Resource
    private ScoScooterMcuMapper scoScooterMcuMapper;


    @Override
    public int deleteByPrimaryKey(Long id) {
        return scoScooterMcuMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(ScoScooterMcu record) {
        return scoScooterMcuMapper.insert(record);
    }

    @Override
    public int insertSelective(ScoScooterMcu record) {
        return scoScooterMcuMapper.insertSelective(record);
    }

    @Override
    public ScoScooterMcu selectByPrimaryKey(Long id) {
        return scoScooterMcuMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(ScoScooterMcu record) {
        return scoScooterMcuMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(ScoScooterMcu record) {
        return scoScooterMcuMapper.updateByPrimaryKey(record);
    }

}
