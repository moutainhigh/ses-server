package com.redescooter.ses.web.delivery.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.delivery.dao.base.CorDeliveryMapper;
import com.redescooter.ses.web.delivery.dm.CorDelivery;
import com.redescooter.ses.web.delivery.service.base.CorDeliveryService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 3/1/2020 3:32 下午
 * @ClassName: ${NAME}
 * @Function: TODO
 */
@Service
public class CorDeliveryServiceImpl extends ServiceImpl<CorDeliveryMapper, CorDelivery> implements CorDeliveryService {

    @Override
    public int updateBatch(List<CorDelivery> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<CorDelivery> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(CorDelivery record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(CorDelivery record) {
        return baseMapper.insertOrUpdateSelective(record);
    }

    @Override
    public int updateBatchSelective(List<CorDelivery> list) {
        return baseMapper.updateBatchSelective(list);
    }
}





