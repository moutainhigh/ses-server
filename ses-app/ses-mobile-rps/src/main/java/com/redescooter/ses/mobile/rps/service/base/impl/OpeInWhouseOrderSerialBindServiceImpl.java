package com.redescooter.ses.mobile.rps.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.redescooter.ses.mobile.rps.dao.base.OpeInWhouseOrderSerialBindMapper;
import com.redescooter.ses.mobile.rps.dm.OpeInWhouseOrderSerialBind;
import com.redescooter.ses.mobile.rps.service.base.OpeInWhouseOrderSerialBindService;

/**
 * @author assert
 * @date 2021/1/22 17:47
 */
@Service
public class OpeInWhouseOrderSerialBindServiceImpl implements OpeInWhouseOrderSerialBindService {

    @Resource
    private OpeInWhouseOrderSerialBindMapper opeInWhouseOrderSerialBindMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return opeInWhouseOrderSerialBindMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insertSelective(OpeInWhouseOrderSerialBind record) {
        return opeInWhouseOrderSerialBindMapper.insertSelective(record);
    }

    @Override
    public OpeInWhouseOrderSerialBind selectByPrimaryKey(Long id) {
        return opeInWhouseOrderSerialBindMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(OpeInWhouseOrderSerialBind record) {
        return opeInWhouseOrderSerialBindMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(OpeInWhouseOrderSerialBind record) {
        return opeInWhouseOrderSerialBindMapper.updateByPrimaryKey(record);
    }

}

