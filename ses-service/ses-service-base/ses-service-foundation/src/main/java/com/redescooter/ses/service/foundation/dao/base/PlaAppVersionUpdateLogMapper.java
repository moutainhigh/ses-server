package com.redescooter.ses.service.foundation.dao.base;

import com.redescooter.ses.service.foundation.dm.base.PlaAppVersionUpdateLog;

/**
 * @author assert
 * @date 2020/12/27 18:15
 */
public interface PlaAppVersionUpdateLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PlaAppVersionUpdateLog record);

    int insertSelective(PlaAppVersionUpdateLog record);

    PlaAppVersionUpdateLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PlaAppVersionUpdateLog record);

    int updateByPrimaryKey(PlaAppVersionUpdateLog record);
}