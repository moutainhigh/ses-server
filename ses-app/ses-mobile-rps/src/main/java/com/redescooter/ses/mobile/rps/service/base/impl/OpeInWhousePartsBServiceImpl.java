package com.redescooter.ses.mobile.rps.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.redescooter.ses.mobile.rps.dao.base.OpeInWhousePartsBMapper;
import com.redescooter.ses.mobile.rps.dm.OpeInWhousePartsB;
import com.redescooter.ses.mobile.rps.service.base.OpeInWhousePartsBService;

/**
 * @author assert
 * @date 2021/1/13 16:13
 */
@Service
public class OpeInWhousePartsBServiceImpl implements OpeInWhousePartsBService {

    @Resource
    private OpeInWhousePartsBMapper opeInWhousePartsBMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return opeInWhousePartsBMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insertSelective(OpeInWhousePartsB record) {
        return opeInWhousePartsBMapper.insertSelective(record);
    }

    @Override
    public OpeInWhousePartsB selectByPrimaryKey(Long id) {
        return opeInWhousePartsBMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(OpeInWhousePartsB record) {
        return opeInWhousePartsBMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(OpeInWhousePartsB record) {
        return opeInWhousePartsBMapper.updateByPrimaryKey(record);
    }
}




