package com.redescooter.ses.mobile.rps.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.mobile.rps.dm.OpeLogisticsOrder;
import java.util.List;
import com.redescooter.ses.mobile.rps.dao.base.OpeLogisticsOrderMapper;
import com.redescooter.ses.mobile.rps.service.base.OpeLogisticsOrderService;
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
