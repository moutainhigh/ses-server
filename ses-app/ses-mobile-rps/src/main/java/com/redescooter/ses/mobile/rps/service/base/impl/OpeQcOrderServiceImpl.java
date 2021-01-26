package com.redescooter.ses.mobile.rps.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.redescooter.ses.mobile.rps.dao.base.OpeQcOrderMapper;
import com.redescooter.ses.mobile.rps.dm.OpeQcOrder;
import com.redescooter.ses.mobile.rps.service.base.OpeQcOrderService;
/**
 *@author assert
 *@date 2021/1/26 10:56
 */
@Service
public class OpeQcOrderServiceImpl implements OpeQcOrderService{

    @Resource
    private OpeQcOrderMapper opeQcOrderMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return opeQcOrderMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insertSelective(OpeQcOrder record) {
        return opeQcOrderMapper.insertSelective(record);
    }

    @Override
    public OpeQcOrder selectByPrimaryKey(Long id) {
        return opeQcOrderMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(OpeQcOrder record) {
        return opeQcOrderMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(OpeQcOrder record) {
        return opeQcOrderMapper.updateByPrimaryKey(record);
    }

}
