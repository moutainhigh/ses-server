package com.redescooter.ses.mobile.rps.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.mobile.rps.dao.base.OpeCombinOrderScooterBMapper;
import com.redescooter.ses.mobile.rps.dm.OpeCombinOrderScooterB;
import org.springframework.stereotype.Service;
import com.redescooter.ses.mobile.rps.service.base.OpeCombinOrderScooterBService;
import java.util.List;

/**
 * @author assert
 * @date 2021/1/27 21:09
 */
@Service
public class OpeCombinOrderScooterBServiceImpl extends ServiceImpl<OpeCombinOrderScooterBMapper, OpeCombinOrderScooterB>
        implements OpeCombinOrderScooterBService {

    @Override
    public int updateBatch(List<OpeCombinOrderScooterB> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int updateBatchSelective(List<OpeCombinOrderScooterB> list) {
        return baseMapper.updateBatchSelective(list);
    }

    @Override
    public int batchInsert(List<OpeCombinOrderScooterB> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeCombinOrderScooterB record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeCombinOrderScooterB record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}


