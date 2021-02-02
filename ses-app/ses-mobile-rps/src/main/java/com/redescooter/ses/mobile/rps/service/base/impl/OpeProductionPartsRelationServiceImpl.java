package com.redescooter.ses.mobile.rps.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.redescooter.ses.mobile.rps.dm.OpeProductionPartsRelation;
import com.redescooter.ses.mobile.rps.dao.base.OpeProductionPartsRelationMapper;
import com.redescooter.ses.mobile.rps.service.base.OpeProductionPartsRelationService;
/**
 *@author assert
 *@date 2021/1/27 21:34
 */
@Service
public class OpeProductionPartsRelationServiceImpl implements OpeProductionPartsRelationService{

    @Resource
    private OpeProductionPartsRelationMapper opeProductionPartsRelationMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return opeProductionPartsRelationMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insertSelective(OpeProductionPartsRelation record) {
        return opeProductionPartsRelationMapper.insertSelective(record);
    }

    @Override
    public OpeProductionPartsRelation selectByPrimaryKey(Long id) {
        return opeProductionPartsRelationMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(OpeProductionPartsRelation record) {
        return opeProductionPartsRelationMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(OpeProductionPartsRelation record) {
        return opeProductionPartsRelationMapper.updateByPrimaryKey(record);
    }

}
