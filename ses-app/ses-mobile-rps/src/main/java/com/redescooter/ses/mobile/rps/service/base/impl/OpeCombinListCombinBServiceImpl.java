package com.redescooter.ses.mobile.rps.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.redescooter.ses.mobile.rps.dao.base.OpeCombinListCombinBMapper;
import com.redescooter.ses.mobile.rps.dm.OpeCombinListCombinB;
import com.redescooter.ses.mobile.rps.service.base.OpeCombinListCombinBService;

/**
 * @author assert
 * @date 2021/1/27 14:12
 */
@Service
public class OpeCombinListCombinBServiceImpl implements OpeCombinListCombinBService {

    @Resource
    private OpeCombinListCombinBMapper opeCombinListCombinBMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return opeCombinListCombinBMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insertSelective(OpeCombinListCombinB record) {
        return opeCombinListCombinBMapper.insertSelective(record);
    }

    @Override
    public OpeCombinListCombinB selectByPrimaryKey(Long id) {
        return opeCombinListCombinBMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(OpeCombinListCombinB record) {
        return opeCombinListCombinBMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(OpeCombinListCombinB record) {
        return opeCombinListCombinBMapper.updateByPrimaryKey(record);
    }

}

