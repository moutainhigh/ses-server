package com.redescooter.ses.mobile.rps.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.redescooter.ses.mobile.rps.dm.OpeOutWhouseOrder;
import com.redescooter.ses.mobile.rps.dao.base.OpeOutWhouseOrderMapper;
import com.redescooter.ses.mobile.rps.service.base.OpeOutWhouseOrderService;
/**
 *@author assert
 *@date 2021/1/27 22:52
 */
@Service
public class OpeOutWhouseOrderServiceImpl implements OpeOutWhouseOrderService{

    @Resource
    private OpeOutWhouseOrderMapper opeOutWhouseOrderMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return opeOutWhouseOrderMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insertSelective(OpeOutWhouseOrder record) {
        return opeOutWhouseOrderMapper.insertSelective(record);
    }

    @Override
    public OpeOutWhouseOrder selectByPrimaryKey(Long id) {
        return opeOutWhouseOrderMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(OpeOutWhouseOrder record) {
        return opeOutWhouseOrderMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(OpeOutWhouseOrder record) {
        return opeOutWhouseOrderMapper.updateByPrimaryKey(record);
    }

}
