package com.redescooter.ses.service.scooter.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.service.scooter.dm.base.ScoScooterBbi;

/**
 * @author assert
 * @date 2020/11/20 14:32
 */
public interface ScoScooterBbiService extends IService<ScoScooterBbi> {


    int deleteByPrimaryKey(Long id);

    int insert(ScoScooterBbi record);

    int insertSelective(ScoScooterBbi record);

    ScoScooterBbi selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ScoScooterBbi record);

    int updateByPrimaryKey(ScoScooterBbi record);

}
