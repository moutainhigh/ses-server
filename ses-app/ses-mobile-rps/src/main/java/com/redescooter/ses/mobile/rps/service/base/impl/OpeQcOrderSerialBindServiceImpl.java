package com.redescooter.ses.mobile.rps.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.redescooter.ses.mobile.rps.dao.base.OpeQcOrderSerialBindMapper;
import com.redescooter.ses.mobile.rps.dm.OpeQcOrderSerialBind;
import com.redescooter.ses.mobile.rps.service.base.OpeQcOrderSerialBindService;

/**
 * @author assert
 * @date 2021/1/25 10:02
 */
@Service
public class OpeQcOrderSerialBindServiceImpl implements OpeQcOrderSerialBindService {

    @Resource
    private OpeQcOrderSerialBindMapper opeQcOrderSerialBindMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return opeQcOrderSerialBindMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insertSelective(OpeQcOrderSerialBind record) {
        return opeQcOrderSerialBindMapper.insertSelective(record);
    }

    @Override
    public OpeQcOrderSerialBind selectByPrimaryKey(Long id) {
        return opeQcOrderSerialBindMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(OpeQcOrderSerialBind record) {
        return opeQcOrderSerialBindMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(OpeQcOrderSerialBind record) {
        return opeQcOrderSerialBindMapper.updateByPrimaryKey(record);
    }

}


