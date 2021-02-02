package com.redescooter.ses.mobile.rps.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.redescooter.ses.mobile.rps.dm.OpeCombinOrder;
import com.redescooter.ses.mobile.rps.dao.base.OpeCombinOrderMapper;
import com.redescooter.ses.mobile.rps.service.base.OpeCombinOrderService;
import java.util.List;

/**
 * @author assert
 * @date 2021/1/22 9:50
 */
@Service
public class OpeCombinOrderServiceImpl extends ServiceImpl<OpeCombinOrderMapper, OpeCombinOrder>
        implements OpeCombinOrderService {

    @Override
    public int updateBatch(List<OpeCombinOrder> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int updateBatchSelective(List<OpeCombinOrder> list) {
        return baseMapper.updateBatchSelective(list);
    }

    @Override
    public int batchInsert(List<OpeCombinOrder> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeCombinOrder record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeCombinOrder record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}




