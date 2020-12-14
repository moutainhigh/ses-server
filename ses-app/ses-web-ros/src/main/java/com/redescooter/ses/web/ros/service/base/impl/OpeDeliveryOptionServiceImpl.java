package com.redescooter.ses.web.ros.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dao.base.OpeDeliveryOptionMapper;
import com.redescooter.ses.web.ros.dm.OpeDeliveryOption;
import com.redescooter.ses.web.ros.service.base.OpeDeliveryOptionService;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class OpeDeliveryOptionServiceImpl extends ServiceImpl<OpeDeliveryOptionMapper, OpeDeliveryOption> implements OpeDeliveryOptionService {

    @Override
    public int updateBatch(List<OpeDeliveryOption> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int updateBatchSelective(List<OpeDeliveryOption> list) {
        return baseMapper.updateBatchSelective(list);
    }
    @Override
    public int batchInsert(List<OpeDeliveryOption> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(OpeDeliveryOption record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(OpeDeliveryOption record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
