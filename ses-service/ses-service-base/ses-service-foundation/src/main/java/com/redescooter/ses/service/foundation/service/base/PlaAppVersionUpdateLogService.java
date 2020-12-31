package com.redescooter.ses.service.foundation.service.base;

import com.redescooter.ses.service.foundation.dm.base.PlaAppVersionUpdateLog;

/**
 * @author assert
 * @date 2020/12/27 15:45
 */
public interface PlaAppVersionUpdateLogService {


    int deleteByPrimaryKey(Long id);

    int insert(PlaAppVersionUpdateLog record);

    int insertSelective(PlaAppVersionUpdateLog record);

    PlaAppVersionUpdateLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PlaAppVersionUpdateLog record);

    int updateByPrimaryKey(PlaAppVersionUpdateLog record);

}


