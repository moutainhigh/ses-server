package com.redescooter.ses.mobile.rps.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.redescooter.ses.mobile.rps.dm.OpeCombinListRelationParts;
import com.redescooter.ses.mobile.rps.dao.base.OpeCombinListRelationPartsMapper;
import com.redescooter.ses.mobile.rps.service.base.OpeCombinListRelationPartsService;
/**
 *@author assert
 *@date 2021/1/27 17:36
 */
@Service
public class OpeCombinListRelationPartsServiceImpl implements OpeCombinListRelationPartsService{

    @Resource
    private OpeCombinListRelationPartsMapper opeCombinListRelationPartsMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return opeCombinListRelationPartsMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insertSelective(OpeCombinListRelationParts record) {
        return opeCombinListRelationPartsMapper.insertSelective(record);
    }

    @Override
    public OpeCombinListRelationParts selectByPrimaryKey(Long id) {
        return opeCombinListRelationPartsMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(OpeCombinListRelationParts record) {
        return opeCombinListRelationPartsMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(OpeCombinListRelationParts record) {
        return opeCombinListRelationPartsMapper.updateByPrimaryKey(record);
    }

}
