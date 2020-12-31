package com.redescooter.ses.service.scooter.service.base.impl;

import com.redescooter.ses.service.scooter.dm.base.ScoScooterMcuControllerInfo;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.redescooter.ses.service.scooter.dao.base.ScoScooterMcuControllerInfoMapper;
import com.redescooter.ses.service.scooter.service.base.ScoScooterMcuControllerInfoService;
/**
*@author assert
*@date 2020/11/20 14:25
*/
@Service
public class ScoScooterMcuControllerInfoServiceImpl implements ScoScooterMcuControllerInfoService{

    @Resource
    private ScoScooterMcuControllerInfoMapper scoScooterMcuControllerInfoMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return scoScooterMcuControllerInfoMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(ScoScooterMcuControllerInfo record) {
        return scoScooterMcuControllerInfoMapper.insert(record);
    }

    @Override
    public int insertSelective(ScoScooterMcuControllerInfo record) {
        return scoScooterMcuControllerInfoMapper.insertSelective(record);
    }

    @Override
    public ScoScooterMcuControllerInfo selectByPrimaryKey(Long id) {
        return scoScooterMcuControllerInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(ScoScooterMcuControllerInfo record) {
        return scoScooterMcuControllerInfoMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(ScoScooterMcuControllerInfo record) {
        return scoScooterMcuControllerInfoMapper.updateByPrimaryKey(record);
    }

}
