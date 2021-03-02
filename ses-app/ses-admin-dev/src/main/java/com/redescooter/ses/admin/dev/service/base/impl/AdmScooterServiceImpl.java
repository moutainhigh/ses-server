package com.redescooter.ses.admin.dev.service.base.impl;

import com.redescooter.ses.admin.dev.dao.base.AdmScooterMapper;
import com.redescooter.ses.admin.dev.dm.AdmScooter;
import com.redescooter.ses.admin.dev.service.base.AdmScooterService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author assert
 * @date 2020/12/10 22:42
 */
@Service
public class AdmScooterServiceImpl implements AdmScooterService {

    @Resource
    private AdmScooterMapper admScooterMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return admScooterMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(AdmScooter record) {
        return admScooterMapper.insert(record);
    }

    @Override
    public int insertSelective(AdmScooter record) {
        return admScooterMapper.insertSelective(record);
    }

    @Override
    public AdmScooter selectByPrimaryKey(Long id) {
        return admScooterMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(AdmScooter record) {
        return admScooterMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(AdmScooter record) {
        return admScooterMapper.updateByPrimaryKey(record);
    }

}
