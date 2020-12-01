package com.redescooter.ses.service.scooter.service.base.impl;

import com.redescooter.ses.service.scooter.dm.base.ScoScooterBbiBatteryWare;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.redescooter.ses.service.scooter.dao.base.ScoScooterBbiBatteryWareMapper;
import com.redescooter.ses.service.scooter.service.base.ScoScooterBbiBatteryWareService;

/**
 * @author assert
 * @date 2020/11/20 14:26
 */
@Service
public class ScoScooterBbiBatteryWareServiceImpl implements ScoScooterBbiBatteryWareService {

    @Resource
    private ScoScooterBbiBatteryWareMapper scoScooterBbiBatteryWareMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return scoScooterBbiBatteryWareMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(ScoScooterBbiBatteryWare record) {
        return scoScooterBbiBatteryWareMapper.insert(record);
    }

    @Override
    public int insertSelective(ScoScooterBbiBatteryWare record) {
        return scoScooterBbiBatteryWareMapper.insertSelective(record);
    }

    @Override
    public ScoScooterBbiBatteryWare selectByPrimaryKey(Long id) {
        return scoScooterBbiBatteryWareMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(ScoScooterBbiBatteryWare record) {
        return scoScooterBbiBatteryWareMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(ScoScooterBbiBatteryWare record) {
        return scoScooterBbiBatteryWareMapper.updateByPrimaryKey(record);
    }

}

