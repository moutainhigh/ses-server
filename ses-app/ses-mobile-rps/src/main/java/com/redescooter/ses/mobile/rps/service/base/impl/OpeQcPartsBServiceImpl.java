package com.redescooter.ses.mobile.rps.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.redescooter.ses.mobile.rps.dm.OpeQcPartsB;
import com.redescooter.ses.mobile.rps.dao.base.OpeQcPartsBMapper;
import com.redescooter.ses.mobile.rps.service.base.OpeQcPartsBService;
/**
 *@author assert
 *@date 2021/1/26 15:38
 */
@Service
public class OpeQcPartsBServiceImpl implements OpeQcPartsBService{

    @Resource
    private OpeQcPartsBMapper opeQcPartsBMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return opeQcPartsBMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insertSelective(OpeQcPartsB record) {
        return opeQcPartsBMapper.insertSelective(record);
    }

    @Override
    public OpeQcPartsB selectByPrimaryKey(Long id) {
        return opeQcPartsBMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(OpeQcPartsB record) {
        return opeQcPartsBMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(OpeQcPartsB record) {
        return opeQcPartsBMapper.updateByPrimaryKey(record);
    }

}
