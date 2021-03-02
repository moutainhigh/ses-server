package com.redescooter.ses.mobile.rps.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.mobile.rps.dao.base.OpeCombinOrderCombinBMapper;
import com.redescooter.ses.mobile.rps.dm.OpeCombinOrderCombinB;
import org.springframework.stereotype.Service;
import com.redescooter.ses.mobile.rps.service.base.OpeCombinOrderCombinBService;
import java.util.List;

/**
 * @author assert
 * @date 2021/1/27 21:09
 */
@Service
public class OpeCombinOrderCombinBServiceImpl extends ServiceImpl<OpeCombinOrderCombinBMapper, OpeCombinOrderCombinB>
        implements OpeCombinOrderCombinBService {

    @Override
    public int updateBatch(List<OpeCombinOrderCombinB> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int updateBatchSelective(List<OpeCombinOrderCombinB> list) {
        return baseMapper.updateBatchSelective(list);
    }

    @Override
    public int batchInsert(List<OpeCombinOrderCombinB> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeCombinOrderCombinB record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeCombinOrderCombinB record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}


