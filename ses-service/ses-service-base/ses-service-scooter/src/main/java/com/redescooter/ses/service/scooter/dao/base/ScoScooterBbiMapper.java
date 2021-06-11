package com.redescooter.ses.service.scooter.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redescooter.ses.service.scooter.dm.base.ScoScooterBbi;

/**
*@author assert
*@date 2020/11/20 14:26
*/
public interface ScoScooterBbiMapper extends BaseMapper<ScoScooterBbi> {
    int deleteByPrimaryKey(Long id);

    int insert(ScoScooterBbi record);

    int insertSelective(ScoScooterBbi record);

    ScoScooterBbi selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ScoScooterBbi record);

    int updateByPrimaryKey(ScoScooterBbi record);

}