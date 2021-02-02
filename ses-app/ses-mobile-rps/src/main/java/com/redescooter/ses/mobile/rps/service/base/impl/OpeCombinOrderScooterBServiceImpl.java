package com.redescooter.ses.mobile.rps.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.redescooter.ses.mobile.rps.dm.OpeCombinOrderScooterB;
import com.redescooter.ses.mobile.rps.dao.base.OpeCombinOrderScooterBMapper;
import com.redescooter.ses.mobile.rps.service.base.OpeCombinOrderScooterBService;
/**
 *@author assert
 *@date 2021/1/27 21:09
 */
@Service
public class OpeCombinOrderScooterBServiceImpl implements OpeCombinOrderScooterBService{

    @Resource
    private OpeCombinOrderScooterBMapper opeCombinOrderScooterBMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return opeCombinOrderScooterBMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insertSelective(OpeCombinOrderScooterB record) {
        return opeCombinOrderScooterBMapper.insertSelective(record);
    }

    @Override
    public OpeCombinOrderScooterB selectByPrimaryKey(Long id) {
        return opeCombinOrderScooterBMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(OpeCombinOrderScooterB record) {
        return opeCombinOrderScooterBMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(OpeCombinOrderScooterB record) {
        return opeCombinOrderScooterBMapper.updateByPrimaryKey(record);
    }

}
