package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.redescooter.ses.web.ros.dm.OpeProductionParts;
import com.redescooter.ses.web.ros.dao.base.OpeProductionPartsMapper;
import java.util.List;
import com.redescooter.ses.web.ros.service.base.OpeProductionPartsService;
@Service
public class OpeProductionPartsServiceImpl implements OpeProductionPartsService{

    @Resource
    private OpeProductionPartsMapper opeProductionPartsMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return opeProductionPartsMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(OpeProductionParts record) {
        return opeProductionPartsMapper.insert(record);
    }

    @Override
    public int insertOrUpdate(OpeProductionParts record) {
        return opeProductionPartsMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeProductionParts record) {
        return opeProductionPartsMapper.insertOrUpdateSelective(record);
    }

    @Override
    public int insertSelective(OpeProductionParts record) {
        return opeProductionPartsMapper.insertSelective(record);
    }

    @Override
    public OpeProductionParts selectByPrimaryKey(Long id) {
        return opeProductionPartsMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(OpeProductionParts record) {
        return opeProductionPartsMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(OpeProductionParts record) {
        return opeProductionPartsMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateBatch(List<OpeProductionParts> list) {
        return opeProductionPartsMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeProductionParts> list) {
        return opeProductionPartsMapper.batchInsert(list);
    }

}
