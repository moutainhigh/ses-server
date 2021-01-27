package com.redescooter.ses.mobile.rps.service.base;

import com.redescooter.ses.mobile.rps.dm.OpeInWhousePartsB;

/**
 * @author assert
 * @date 2021/1/13 16:13
 */
public interface OpeInWhousePartsBService {

    int deleteByPrimaryKey(Long id);

    int insertSelective(OpeInWhousePartsB record);

    OpeInWhousePartsB selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OpeInWhousePartsB record);

    int updateByPrimaryKey(OpeInWhousePartsB record);
}




