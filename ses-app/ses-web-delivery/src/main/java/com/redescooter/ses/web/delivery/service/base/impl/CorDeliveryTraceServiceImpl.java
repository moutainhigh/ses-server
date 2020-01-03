package com.redescooter.ses.web.delivery.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.delivery.dao.base.CorDeliveryTraceMapper;
import com.redescooter.ses.web.delivery.dm.CorDeliveryTrace;
import com.redescooter.ses.web.delivery.service.base.CorDeliveryTraceService;
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
public class CorDeliveryTraceServiceImpl extends ServiceImpl<CorDeliveryTraceMapper, CorDeliveryTrace> implements CorDeliveryTraceService {

    @Override
    public int batchInsert(List<CorDeliveryTrace> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(CorDeliveryTrace record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(CorDeliveryTrace record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
