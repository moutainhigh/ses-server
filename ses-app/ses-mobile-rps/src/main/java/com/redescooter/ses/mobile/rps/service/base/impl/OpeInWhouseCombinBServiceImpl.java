package com.redescooter.ses.mobile.rps.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.redescooter.ses.mobile.rps.dm.OpeInWhouseCombinB;
import com.redescooter.ses.mobile.rps.dao.base.OpeInWhouseCombinBMapper;
import com.redescooter.ses.mobile.rps.service.base.OpeInWhouseCombinBService;

/**
 * @author assert
 * @date 2021/1/13 16:13
 */
@Service
public class OpeInWhouseCombinBServiceImpl implements OpeInWhouseCombinBService {

    @Resource
    private OpeInWhouseCombinBMapper opeInWhouseCombinBMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return opeInWhouseCombinBMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insertSelective(OpeInWhouseCombinB record) {
        return opeInWhouseCombinBMapper.insertSelective(record);
    }

    @Override
    public OpeInWhouseCombinB selectByPrimaryKey(Long id) {
        return opeInWhouseCombinBMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(OpeInWhouseCombinB record) {
        return opeInWhouseCombinBMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(OpeInWhouseCombinB record) {
        return opeInWhouseCombinBMapper.updateByPrimaryKey(record);
    }
}




