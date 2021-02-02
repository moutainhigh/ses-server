package com.redescooter.ses.mobile.rps.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.mobile.rps.dao.base.OpeQcScooterBMapper;
import com.redescooter.ses.mobile.rps.dm.OpeQcScooterB;
import org.springframework.stereotype.Service;
import com.redescooter.ses.mobile.rps.service.base.OpeQcScooterBService;
import java.util.List;

/**
 * @author assert
 * @date 2021/1/26 15:38
 */
@Service
public class OpeQcScooterBServiceImpl extends ServiceImpl<OpeQcScooterBMapper, OpeQcScooterB>
        implements OpeQcScooterBService {

    @Override
    public int updateBatch(List<OpeQcScooterB> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int updateBatchSelective(List<OpeQcScooterB> list) {
        return baseMapper.updateBatchSelective(list);
    }

    @Override
    public int batchInsert(List<OpeQcScooterB> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeQcScooterB record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeQcScooterB record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}


