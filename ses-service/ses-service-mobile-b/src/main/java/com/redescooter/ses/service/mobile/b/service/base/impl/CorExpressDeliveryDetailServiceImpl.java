package com.redescooter.ses.service.mobile.b.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.service.mobile.b.dao.base.CorExpressDeliveryDetailMapper;
import java.util.List;
import com.redescooter.ses.service.mobile.b.dm.base.CorExpressDeliveryDetail;
import com.redescooter.ses.service.mobile.b.dm.base.CorExpressDeliveryDetailExample;
import com.redescooter.ses.service.mobile.b.service.base.CorExpressDeliveryDetailService;
@Service
public class CorExpressDeliveryDetailServiceImpl extends ServiceImpl<CorExpressDeliveryDetailMapper, CorExpressDeliveryDetail> implements CorExpressDeliveryDetailService{

    @Override
    public long countByExample(CorExpressDeliveryDetailExample example) {
        return baseMapper.countByExample(example);
    }
    @Override
    public int deleteByExample(CorExpressDeliveryDetailExample example) {
        return baseMapper.deleteByExample(example);
    }
    @Override
    public List<CorExpressDeliveryDetail> selectByExample(CorExpressDeliveryDetailExample example) {
        return baseMapper.selectByExample(example);
    }
    @Override
    public int updateByExampleSelective(CorExpressDeliveryDetail record,CorExpressDeliveryDetailExample example) {
        return baseMapper.updateByExampleSelective(record,example);
    }
    @Override
    public int updateByExample(CorExpressDeliveryDetail record,CorExpressDeliveryDetailExample example) {
        return baseMapper.updateByExample(record,example);
    }
}
