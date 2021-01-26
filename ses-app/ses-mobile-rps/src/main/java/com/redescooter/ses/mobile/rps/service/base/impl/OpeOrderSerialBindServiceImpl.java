package com.redescooter.ses.mobile.rps.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.redescooter.ses.mobile.rps.dao.base.OpeOrderSerialBindMapper;
import com.redescooter.ses.mobile.rps.dm.OpeOrderSerialBind;
import com.redescooter.ses.mobile.rps.service.base.OpeOrderSerialBindService;

/**
 * @author assert
 * @date 2020/12/30 15:26
 */
@Service
public class OpeOrderSerialBindServiceImpl implements OpeOrderSerialBindService {

    @Resource
    private OpeOrderSerialBindMapper opeOrderSerialBindMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return opeOrderSerialBindMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(OpeOrderSerialBind record) {
        return opeOrderSerialBindMapper.insert(record);
    }

    @Override
    public int insertSelective(OpeOrderSerialBind record) {
        return opeOrderSerialBindMapper.insertSelective(record);
    }

    @Override
    public OpeOrderSerialBind selectByPrimaryKey(Long id) {
        return opeOrderSerialBindMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(OpeOrderSerialBind record) {
        return opeOrderSerialBindMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(OpeOrderSerialBind record) {
        return opeOrderSerialBindMapper.updateByPrimaryKey(record);
    }

}

