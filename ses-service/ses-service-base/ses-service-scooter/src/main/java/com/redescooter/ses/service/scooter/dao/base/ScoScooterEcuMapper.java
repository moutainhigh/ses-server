package com.redescooter.ses.service.scooter.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.service.scooter.dm.base.ScoScooterEcu;

/**
 * @author assert
 * @date 2020/11/27 17:46
 */
public interface ScoScooterEcuMapper extends BaseMapper<ScoScooterEcu> {
    int deleteByPrimaryKey(Long id);

    int insert(ScoScooterEcu record);

    int insertSelective(ScoScooterEcu record);

    ScoScooterEcu selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ScoScooterEcu record);

    int updateByPrimaryKey(ScoScooterEcu record);

}