package com.redescooter.ses.web.ros.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dao.base.OpeLogisticsOrderMapper;
import com.redescooter.ses.web.ros.dm.OpeLogisticsOrder;
import com.redescooter.ses.web.ros.service.base.OpeLogisticsOrderService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OpeLogisticsOrderServiceImpl extends ServiceImpl<OpeLogisticsOrderMapper, OpeLogisticsOrder> implements OpeLogisticsOrderService{

    @Override
    public int updateBatch(List<OpeLogisticsOrder> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int batchInsert(List<OpeLogisticsOrder> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(OpeLogisticsOrder record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(OpeLogisticsOrder record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
