package com.redescooter.ses.mobile.rps.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.redescooter.ses.mobile.rps.dm.OpeCombinOrderCombinB;
import com.redescooter.ses.mobile.rps.dao.base.OpeCombinOrderCombinBMapper;
import com.redescooter.ses.mobile.rps.service.base.OpeCombinOrderCombinBService;
/**
 *@author assert
 *@date 2021/1/27 21:09
 */
@Service
public class OpeCombinOrderCombinBServiceImpl implements OpeCombinOrderCombinBService{

    @Resource
    private OpeCombinOrderCombinBMapper opeCombinOrderCombinBMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return opeCombinOrderCombinBMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insertSelective(OpeCombinOrderCombinB record) {
        return opeCombinOrderCombinBMapper.insertSelective(record);
    }

    @Override
    public OpeCombinOrderCombinB selectByPrimaryKey(Integer id) {
        return opeCombinOrderCombinBMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(OpeCombinOrderCombinB record) {
        return opeCombinOrderCombinBMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(OpeCombinOrderCombinB record) {
        return opeCombinOrderCombinBMapper.updateByPrimaryKey(record);
    }

}
