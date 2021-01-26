package com.redescooter.ses.mobile.rps.service.base.impl;

import com.redescooter.ses.mobile.rps.dm.OpeInWhouseScooterB;import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.redescooter.ses.mobile.rps.dao.base.OpeInWhouseScooterBMapper;
import com.redescooter.ses.mobile.rps.service.base.OpeInWhouseScooterBService;import java.util.List;

/**
 * @author assert
 * @date 2021/1/13 15:53
 */
@Service
public class OpeInWhouseScooterBServiceImpl implements OpeInWhouseScooterBService {

    @Resource
    private OpeInWhouseScooterBMapper opeInWhouseScooterBMapper;

    @Override
    public int updateBatch(List<OpeInWhouseScooterB> list) {
        return opeInWhouseScooterBMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeInWhouseScooterB> list) {
        return opeInWhouseScooterBMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeInWhouseScooterB record) {
        return opeInWhouseScooterBMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeInWhouseScooterB record) {
        return opeInWhouseScooterBMapper.insertOrUpdateSelective(record);
    }
}




