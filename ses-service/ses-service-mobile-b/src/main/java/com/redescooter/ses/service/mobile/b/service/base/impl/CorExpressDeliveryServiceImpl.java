package com.redescooter.ses.service.mobile.b.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.service.mobile.b.dm.base.CorExpressDelivery;
import java.util.List;
import com.redescooter.ses.service.mobile.b.dao.base.CorExpressDeliveryMapper;
import com.redescooter.ses.service.mobile.b.dm.base.CorExpressDeliveryExample;
import com.redescooter.ses.service.mobile.b.service.base.CorExpressDeliveryService;
@Service
public class CorExpressDeliveryServiceImpl extends ServiceImpl<CorExpressDeliveryMapper, CorExpressDelivery> implements CorExpressDeliveryService{

    @Override
    public long countByExample(CorExpressDeliveryExample example) {
        return baseMapper.countByExample(example);
    }
    @Override
    public int deleteByExample(CorExpressDeliveryExample example) {
        return baseMapper.deleteByExample(example);
    }
    @Override
    public List<CorExpressDelivery> selectByExample(CorExpressDeliveryExample example) {
        return baseMapper.selectByExample(example);
    }
    @Override
    public int updateByExampleSelective(CorExpressDelivery record,CorExpressDeliveryExample example) {
        return baseMapper.updateByExampleSelective(record,example);
    }
    @Override
    public int updateByExample(CorExpressDelivery record,CorExpressDeliveryExample example) {
        return baseMapper.updateByExample(record,example);
    }
}
