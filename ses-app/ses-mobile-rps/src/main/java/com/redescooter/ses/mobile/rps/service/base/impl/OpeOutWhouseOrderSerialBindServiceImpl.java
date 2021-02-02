package com.redescooter.ses.mobile.rps.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.redescooter.ses.mobile.rps.dm.OpeOutWhouseOrderSerialBind;
import com.redescooter.ses.mobile.rps.dao.base.OpeOutWhouseOrderSerialBindMapper;
import com.redescooter.ses.mobile.rps.service.base.OpeOutWhouseOrderSerialBindService;

/**
 * @author assert
 * @date 2021/1/25 10:46
 */
@Service
public class OpeOutWhouseOrderSerialBindServiceImpl implements OpeOutWhouseOrderSerialBindService {

    @Resource
    private OpeOutWhouseOrderSerialBindMapper opeOutWhouseOrderSerialBindMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return opeOutWhouseOrderSerialBindMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insertSelective(OpeOutWhouseOrderSerialBind record) {
        return opeOutWhouseOrderSerialBindMapper.insertSelective(record);
    }

    @Override
    public OpeOutWhouseOrderSerialBind selectByPrimaryKey(Long id) {
        return opeOutWhouseOrderSerialBindMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(OpeOutWhouseOrderSerialBind record) {
        return opeOutWhouseOrderSerialBindMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(OpeOutWhouseOrderSerialBind record) {
        return opeOutWhouseOrderSerialBindMapper.updateByPrimaryKey(record);
    }

}


