package com.redescooter.ses.admin.dev.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.redescooter.ses.admin.dev.dm.AdmScooterParts;
import com.redescooter.ses.admin.dev.dao.base.AdmScooterPartsMapper;
import com.redescooter.ses.admin.dev.service.base.AdmScooterPartsService;
/**
*@author assert
*@date 2020/12/10 22:43
*/
@Service
public class AdmScooterPartsServiceImpl implements AdmScooterPartsService{

    @Resource
    private AdmScooterPartsMapper admScooterPartsMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return admScooterPartsMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(AdmScooterParts record) {
        return admScooterPartsMapper.insert(record);
    }

    @Override
    public int insertSelective(AdmScooterParts record) {
        return admScooterPartsMapper.insertSelective(record);
    }

    @Override
    public AdmScooterParts selectByPrimaryKey(Long id) {
        return admScooterPartsMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(AdmScooterParts record) {
        return admScooterPartsMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(AdmScooterParts record) {
        return admScooterPartsMapper.updateByPrimaryKey(record);
    }

}
