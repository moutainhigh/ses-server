package com.redescooter.ses.service.scooter.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redescooter.ses.service.scooter.dm.base.ScoScooterBbiBatteryWare;

/**
 * @author assert
 * @date 2020/11/20 14:32
 */
public interface ScoScooterBbiBatteryWareService extends IService<ScoScooterBbiBatteryWare> {


    int deleteByPrimaryKey(Long id);

    int insert(ScoScooterBbiBatteryWare record);

    int insertSelective(ScoScooterBbiBatteryWare record);

    ScoScooterBbiBatteryWare selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ScoScooterBbiBatteryWare record);

    int updateByPrimaryKey(ScoScooterBbiBatteryWare record);

}

