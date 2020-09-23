package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.redescooter.ses.web.ros.dm.OpeProductionPartsDraft;
import com.redescooter.ses.web.ros.dao.base.OpeProductionPartsDraftMapper;
import com.redescooter.ses.web.ros.service.base.OpeProductionPartsDraftService;

@Service
public class OpeProductionPartsDraftServiceImpl implements OpeProductionPartsDraftService {

    @Resource
    private OpeProductionPartsDraftMapper opeProductionPartsDraftMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return opeProductionPartsDraftMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(OpeProductionPartsDraft record) {
        return opeProductionPartsDraftMapper.insert(record);
    }

    @Override
    public int insertOrUpdate(OpeProductionPartsDraft record) {
        return opeProductionPartsDraftMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeProductionPartsDraft record) {
        return opeProductionPartsDraftMapper.insertOrUpdateSelective(record);
    }

    @Override
    public int insertSelective(OpeProductionPartsDraft record) {
        return opeProductionPartsDraftMapper.insertSelective(record);
    }

    @Override
    public OpeProductionPartsDraft selectByPrimaryKey(Long id) {
        return opeProductionPartsDraftMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(OpeProductionPartsDraft record) {
        return opeProductionPartsDraftMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(OpeProductionPartsDraft record) {
        return opeProductionPartsDraftMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateBatch(List<OpeProductionPartsDraft> list) {
        return opeProductionPartsDraftMapper.updateBatch(list);
    }

    @Override
    public int updateBatchSelective(List<OpeProductionPartsDraft> list) {
        return opeProductionPartsDraftMapper.updateBatchSelective(list);
    }

    @Override
    public int batchInsert(List<OpeProductionPartsDraft> list) {
        return opeProductionPartsDraftMapper.batchInsert(list);
    }

}

