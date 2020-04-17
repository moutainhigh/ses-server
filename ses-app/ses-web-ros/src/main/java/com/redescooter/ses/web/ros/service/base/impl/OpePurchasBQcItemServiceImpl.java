package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.redescooter.ses.web.ros.dm.OpePurchasBQcItem;
import com.redescooter.ses.web.ros.dao.base.OpePurchasBQcItemMapper;
import com.redescooter.ses.web.ros.service.base.OpePurchasBQcItemService;
@Service
public class OpePurchasBQcItemServiceImpl extends ServiceImpl<OpePurchasBQcItemMapper, OpePurchasBQcItem> implements OpePurchasBQcItemService{

    @Override
    public int updateBatch(List<OpePurchasBQcItem> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int batchInsert(List<OpePurchasBQcItem> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(OpePurchasBQcItem record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(OpePurchasBQcItem record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
