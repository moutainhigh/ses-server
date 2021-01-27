package com.redescooter.ses.mobile.rps.service.base;

import com.redescooter.ses.mobile.rps.dm.OpeInWhouseCombinB;

/**
 * @author assert
 * @date 2021/1/13 16:13
 */
public interface OpeInWhouseCombinBService {


    int deleteByPrimaryKey(Long id);

    int insertSelective(OpeInWhouseCombinB record);

    OpeInWhouseCombinB selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OpeInWhouseCombinB record);

    int updateByPrimaryKey(OpeInWhouseCombinB record);
}




