package com.redescooter.ses.mobile.rps.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.redescooter.ses.mobile.rps.dao.base.OpeCombinListPartsSerialBindMapper;
import com.redescooter.ses.mobile.rps.dm.OpeCombinListPartsSerialBind;
import com.redescooter.ses.mobile.rps.service.base.OpeCombinListPartsSerialBindService;

/**
 * @author assert
 * @date 2021/1/27 17:37
 */
@Service
public class OpeCombinListPartsSerialBindServiceImpl implements OpeCombinListPartsSerialBindService {

    @Resource
    private OpeCombinListPartsSerialBindMapper opeCombinListPartsSerialBindMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return opeCombinListPartsSerialBindMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insertSelective(OpeCombinListPartsSerialBind record) {
        return opeCombinListPartsSerialBindMapper.insertSelective(record);
    }

    @Override
    public OpeCombinListPartsSerialBind selectByPrimaryKey(Long id) {
        return opeCombinListPartsSerialBindMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(OpeCombinListPartsSerialBind record) {
        return opeCombinListPartsSerialBindMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(OpeCombinListPartsSerialBind record) {
        return opeCombinListPartsSerialBindMapper.updateByPrimaryKey(record);
    }

}

