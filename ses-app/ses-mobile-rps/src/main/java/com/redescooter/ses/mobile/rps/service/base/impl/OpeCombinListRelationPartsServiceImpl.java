package com.redescooter.ses.mobile.rps.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.mobile.rps.dao.base.OpeCombinListRelationPartsMapper;
import com.redescooter.ses.mobile.rps.dm.OpeCombinListRelationParts;
import org.springframework.stereotype.Service;
import com.redescooter.ses.mobile.rps.service.base.OpeCombinListRelationPartsService;
import java.util.List;

/**
 * @author assert
 * @date 2021/1/27 17:36
 */
@Service
public class OpeCombinListRelationPartsServiceImpl extends ServiceImpl<OpeCombinListRelationPartsMapper, OpeCombinListRelationParts>
        implements OpeCombinListRelationPartsService {

    @Override
    public int updateBatch(List<OpeCombinListRelationParts> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int updateBatchSelective(List<OpeCombinListRelationParts> list) {
        return baseMapper.updateBatchSelective(list);
    }

    @Override
    public int batchInsert(List<OpeCombinListRelationParts> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeCombinListRelationParts record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeCombinListRelationParts record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}


