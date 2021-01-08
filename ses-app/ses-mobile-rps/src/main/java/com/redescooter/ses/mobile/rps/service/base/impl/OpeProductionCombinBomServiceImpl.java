package com.redescooter.ses.mobile.rps.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.redescooter.ses.mobile.rps.dao.base.OpeProductionCombinBomMapper;
import com.redescooter.ses.mobile.rps.dm.OpeProductionCombinBom;
import com.redescooter.ses.mobile.rps.service.base.OpeProductionCombinBomService;
/**
*@author assert
*@date 2021/1/8 13:48
*/
@Service
public class OpeProductionCombinBomServiceImpl implements OpeProductionCombinBomService{

    @Resource
    private OpeProductionCombinBomMapper opeProductionCombinBomMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return opeProductionCombinBomMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(OpeProductionCombinBom record) {
        return opeProductionCombinBomMapper.insert(record);
    }

    @Override
    public int insertSelective(OpeProductionCombinBom record) {
        return opeProductionCombinBomMapper.insertSelective(record);
    }

    @Override
    public OpeProductionCombinBom selectByPrimaryKey(Long id) {
        return opeProductionCombinBomMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(OpeProductionCombinBom record) {
        return opeProductionCombinBomMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(OpeProductionCombinBom record) {
        return opeProductionCombinBomMapper.updateByPrimaryKey(record);
    }

}
