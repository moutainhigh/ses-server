package com.redescooter.ses.service.scooter.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.service.scooter.dm.base.ScoScooterMcuControllerInfo;

/**
 * @author assert
 * @date 2020/11/20 14:32
 */
public interface ScoScooterMcuControllerInfoMapper extends BaseMapper<ScoScooterMcuControllerInfo> {

    int deleteByPrimaryKey(Long id);

    int insert(ScoScooterMcuControllerInfo record);

    int insertSelective(ScoScooterMcuControllerInfo record);

    ScoScooterMcuControllerInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ScoScooterMcuControllerInfo record);

    int updateByPrimaryKey(ScoScooterMcuControllerInfo record);

}