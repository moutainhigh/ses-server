package com.redescooter.ses.mobile.rps.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.redescooter.ses.mobile.rps.dao.base.OpeQcScooterBMapper;
import com.redescooter.ses.mobile.rps.dm.OpeQcScooterB;
import com.redescooter.ses.mobile.rps.service.base.OpeQcScooterBService;
/**
 *@author assert
 *@date 2021/1/26 15:38
 */
@Service
public class OpeQcScooterBServiceImpl implements OpeQcScooterBService{

    @Resource
    private OpeQcScooterBMapper opeQcScooterBMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return opeQcScooterBMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insertSelective(OpeQcScooterB record) {
        return opeQcScooterBMapper.insertSelective(record);
    }

    @Override
    public OpeQcScooterB selectByPrimaryKey(Long id) {
        return opeQcScooterBMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(OpeQcScooterB record) {
        return opeQcScooterBMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(OpeQcScooterB record) {
        return opeQcScooterBMapper.updateByPrimaryKey(record);
    }

}
