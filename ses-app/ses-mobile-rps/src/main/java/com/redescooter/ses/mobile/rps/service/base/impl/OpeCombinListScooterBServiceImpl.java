package com.redescooter.ses.mobile.rps.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.redescooter.ses.mobile.rps.dao.base.OpeCombinListScooterBMapper;
import com.redescooter.ses.mobile.rps.dm.OpeCombinListScooterB;
import com.redescooter.ses.mobile.rps.service.base.OpeCombinListScooterBService;

/**
 * @author assert
 * @date 2021/1/27 14:12
 */
@Service
public class OpeCombinListScooterBServiceImpl implements OpeCombinListScooterBService {

    @Resource
    private OpeCombinListScooterBMapper opeCombinListScooterBMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return opeCombinListScooterBMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insertSelective(OpeCombinListScooterB record) {
        return opeCombinListScooterBMapper.insertSelective(record);
    }

    @Override
    public OpeCombinListScooterB selectByPrimaryKey(Long id) {
        return opeCombinListScooterBMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(OpeCombinListScooterB record) {
        return opeCombinListScooterBMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(OpeCombinListScooterB record) {
        return opeCombinListScooterBMapper.updateByPrimaryKey(record);
    }

}

