package com.redescooter.ses.mobile.rps.service.base;

import com.redescooter.ses.mobile.rps.dm.OpeWmsStockSerialNumber;

/**
 * @author assert
 * @date 2021/1/22 20:10
 */
public interface OpeWmsStockSerialNumberService {


    int deleteByPrimaryKey(Long id);

    int insertSelective(OpeWmsStockSerialNumber record);

    OpeWmsStockSerialNumber selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OpeWmsStockSerialNumber record);

    int updateByPrimaryKey(OpeWmsStockSerialNumber record);

}

