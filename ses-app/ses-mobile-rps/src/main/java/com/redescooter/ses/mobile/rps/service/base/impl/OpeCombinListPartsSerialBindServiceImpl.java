package com.redescooter.ses.mobile.rps.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.mobile.rps.dao.base.OpeCombinListPartsSerialBindMapper;
import com.redescooter.ses.mobile.rps.dm.OpeCombinListPartsSerialBind;
import org.springframework.stereotype.Service;
import com.redescooter.ses.mobile.rps.service.base.OpeCombinListPartsSerialBindService;
import java.util.List;

/**
 * @author assert
 * @date 2021/1/27 17:37
 */
@Service
public class OpeCombinListPartsSerialBindServiceImpl extends ServiceImpl<OpeCombinListPartsSerialBindMapper, OpeCombinListPartsSerialBind>
        implements OpeCombinListPartsSerialBindService {

    @Override
    public int updateBatch(List<OpeCombinListPartsSerialBind> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int updateBatchSelective(List<OpeCombinListPartsSerialBind> list) {
        return baseMapper.updateBatchSelective(list);
    }

    @Override
    public int batchInsert(List<OpeCombinListPartsSerialBind> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeCombinListPartsSerialBind record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeCombinListPartsSerialBind record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}



