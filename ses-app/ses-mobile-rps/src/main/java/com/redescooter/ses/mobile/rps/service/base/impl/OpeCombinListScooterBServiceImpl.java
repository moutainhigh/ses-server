package com.redescooter.ses.mobile.rps.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.mobile.rps.dao.base.OpeCombinListScooterBMapper;
import com.redescooter.ses.mobile.rps.dm.OpeCombinListScooterB;
import org.springframework.stereotype.Service;
import com.redescooter.ses.mobile.rps.service.base.OpeCombinListScooterBService;
import java.util.List;

/**
 * @author assert
 * @date 2021/1/27 14:12
 */
@Service
public class OpeCombinListScooterBServiceImpl extends ServiceImpl<OpeCombinListScooterBMapper, OpeCombinListScooterB>
        implements OpeCombinListScooterBService {

    @Override
    public int updateBatch(List<OpeCombinListScooterB> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int updateBatchSelective(List<OpeCombinListScooterB> list) {
        return baseMapper.updateBatchSelective(list);
    }

    @Override
    public int batchInsert(List<OpeCombinListScooterB> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeCombinListScooterB record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeCombinListScooterB record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}



