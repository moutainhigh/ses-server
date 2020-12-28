package com.redescooter.ses.service.foundation.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.redescooter.ses.service.foundation.dm.base.PlaAppVersionUpdateLog;
import com.redescooter.ses.service.foundation.dao.base.PlaAppVersionUpdateLogMapper;

/**
 * @author assert
 * @date 2020/12/27 15:45
 */
@Service
public class PlaAppVersionUpdateLogServiceImpl implements PlaAppVersionUpdateLogService {

    @Resource
    private PlaAppVersionUpdateLogMapper plaAppVersionUpdateLogMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return plaAppVersionUpdateLogMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(PlaAppVersionUpdateLog record) {
        return plaAppVersionUpdateLogMapper.insert(record);
    }

    @Override
    public int insertSelective(PlaAppVersionUpdateLog record) {
        return plaAppVersionUpdateLogMapper.insertSelective(record);
    }

    @Override
    public PlaAppVersionUpdateLog selectByPrimaryKey(Long id) {
        return plaAppVersionUpdateLogMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(PlaAppVersionUpdateLog record) {
        return plaAppVersionUpdateLogMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(PlaAppVersionUpdateLog record) {
        return plaAppVersionUpdateLogMapper.updateByPrimaryKey(record);
    }

}


