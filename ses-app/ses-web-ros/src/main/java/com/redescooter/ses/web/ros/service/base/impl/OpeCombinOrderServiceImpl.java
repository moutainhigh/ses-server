package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.redescooter.ses.web.ros.dao.base.OpeCombinOrderMapper;
import com.redescooter.ses.web.ros.dm.OpeCombinOrder;
import com.redescooter.ses.web.ros.service.base.OpeCombinOrderService;

@Service
public class OpeCombinOrderServiceImpl extends ServiceImpl<OpeCombinOrderMapper, OpeCombinOrder> implements OpeCombinOrderService {

    @Override
    public int updateBatch(List<OpeCombinOrder> list) {
        return baseMapper.updateBatch(list);
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


