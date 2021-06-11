package com.redescooter.ses.service.scooter.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.service.scooter.dao.base.ScoScooterBbiMapper;
import com.redescooter.ses.service.scooter.dm.base.ScoScooterBbi;
import com.redescooter.ses.service.scooter.service.base.ScoScooterBbiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author assert
 * @date 2020/11/20 14:32
 */
@Slf4j
@Service
public class ScoScooterBbiServiceImpl extends ServiceImpl<ScoScooterBbiMapper, ScoScooterBbi> implements ScoScooterBbiService{

    @Resource
    private ScoScooterBbiMapper scoScooterBbiMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return scoScooterBbiMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(ScoScooterBbi record) {
        return scoScooterBbiMapper.insert(record);
    }

    @Override
    public int insertSelective(ScoScooterBbi record) {
        return scoScooterBbiMapper.insertSelective(record);
    }

    @Override
    public ScoScooterBbi selectByPrimaryKey(Long id) {
        return scoScooterBbiMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(ScoScooterBbi record) {
        return scoScooterBbiMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(ScoScooterBbi record) {
        return scoScooterBbiMapper.updateByPrimaryKey(record);
    }

}
