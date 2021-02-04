package com.redescooter.ses.mobile.rps.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.mobile.rps.dao.base.OpeCombinListCombinBMapper;
import com.redescooter.ses.mobile.rps.dm.OpeCombinListCombinB;
import org.springframework.stereotype.Service;
import com.redescooter.ses.mobile.rps.service.base.OpeCombinListCombinBService;
import java.util.List;

/**
 * @author assert
 * @date 2021/1/27 14:12
 */
@Service
public class OpeCombinListCombinBServiceImpl extends ServiceImpl<OpeCombinListCombinBMapper, OpeCombinListCombinB>
        implements OpeCombinListCombinBService {

    @Override
    public int updateBatch(List<OpeCombinListCombinB> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int updateBatchSelective(List<OpeCombinListCombinB> list) {
        return baseMapper.updateBatchSelective(list);
    }

    @Override
    public int batchInsert(List<OpeCombinListCombinB> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeCombinListCombinB record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeCombinListCombinB record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}



