package com.redescooter.ses.mobile.rps.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.mobile.rps.dao.base.OpeInWhouseScooterBMapper;
import com.redescooter.ses.mobile.rps.dm.OpeInWhouseScooterB;
import org.springframework.stereotype.Service;
import com.redescooter.ses.mobile.rps.service.base.OpeInWhouseScooterBService;
import java.util.List;

/**
 * @author assert
 * @date 2021/1/13 15:53
 */
@Service
public class OpeInWhouseScooterBServiceImpl extends ServiceImpl<OpeInWhouseScooterBMapper, OpeInWhouseScooterB>
        implements OpeInWhouseScooterBService {

    @Override
    public int updateBatch(List<OpeInWhouseScooterB> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int updateBatchSelective(List<OpeInWhouseScooterB> list) {
        return baseMapper.updateBatchSelective(list);
    }

    @Override
    public int batchInsert(List<OpeInWhouseScooterB> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeInWhouseScooterB record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeInWhouseScooterB record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}









