package com.redescooter.ses.mobile.rps.service.base.impl;

import com.redescooter.ses.mobile.rps.dm.OpeInWhouseScooterB;import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.redescooter.ses.mobile.rps.dao.base.OpeInWhouseScooterBMapper;
import com.redescooter.ses.mobile.rps.service.base.OpeInWhouseScooterBService;

/**
 * @author assert
 * @date 2021/1/13 15:53
 */
@Service
public class OpeInWhouseScooterBServiceImpl implements OpeInWhouseScooterBService {

    @Resource
    private OpeInWhouseScooterBMapper opeInWhouseScooterBMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return opeInWhouseScooterBMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insertSelective(OpeInWhouseScooterB record) {
        return opeInWhouseScooterBMapper.insertSelective(record);
    }

    @Override
    public OpeInWhouseScooterB selectByPrimaryKey(Long id) {
        return opeInWhouseScooterBMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(OpeInWhouseScooterB record) {
        return opeInWhouseScooterBMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(OpeInWhouseScooterB record) {
        return opeInWhouseScooterBMapper.updateByPrimaryKey(record);
    }
}







