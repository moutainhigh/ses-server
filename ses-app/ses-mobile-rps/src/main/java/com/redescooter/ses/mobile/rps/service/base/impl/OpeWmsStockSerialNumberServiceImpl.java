package com.redescooter.ses.mobile.rps.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.redescooter.ses.mobile.rps.dao.base.OpeWmsStockSerialNumberMapper;
import com.redescooter.ses.mobile.rps.dm.OpeWmsStockSerialNumber;
import com.redescooter.ses.mobile.rps.service.base.OpeWmsStockSerialNumberService;

/**
 * @author assert
 * @date 2021/1/22 20:10
 */
@Service
public class OpeWmsStockSerialNumberServiceImpl implements OpeWmsStockSerialNumberService {

    @Resource
    private OpeWmsStockSerialNumberMapper opeWmsStockSerialNumberMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return opeWmsStockSerialNumberMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insertSelective(OpeWmsStockSerialNumber record) {
        return opeWmsStockSerialNumberMapper.insertSelective(record);
    }

    @Override
    public OpeWmsStockSerialNumber selectByPrimaryKey(Long id) {
        return opeWmsStockSerialNumberMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(OpeWmsStockSerialNumber record) {
        return opeWmsStockSerialNumberMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(OpeWmsStockSerialNumber record) {
        return opeWmsStockSerialNumberMapper.updateByPrimaryKey(record);
    }

}

