package com.redescooter.ses.mobile.rps.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.redescooter.ses.mobile.rps.dm.OpeQcCombinB;
import com.redescooter.ses.mobile.rps.dao.base.OpeQcCombinBMapper;
import com.redescooter.ses.mobile.rps.service.base.OpeQcCombinBService;
/**
 *@author assert
 *@date 2021/1/26 15:38
 */
@Service
public class OpeQcCombinBServiceImpl implements OpeQcCombinBService{

    @Resource
    private OpeQcCombinBMapper opeQcCombinBMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return opeQcCombinBMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insertSelective(OpeQcCombinB record) {
        return opeQcCombinBMapper.insertSelective(record);
    }

    @Override
    public OpeQcCombinB selectByPrimaryKey(Long id) {
        return opeQcCombinBMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(OpeQcCombinB record) {
        return opeQcCombinBMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(OpeQcCombinB record) {
        return opeQcCombinBMapper.updateByPrimaryKey(record);
    }

}
