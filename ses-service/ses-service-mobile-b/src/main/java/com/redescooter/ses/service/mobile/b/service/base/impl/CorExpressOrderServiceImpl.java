package com.redescooter.ses.service.mobile.b.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.service.mobile.b.dm.base.CorExpressOrder;
import java.util.List;
import com.redescooter.ses.service.mobile.b.dao.base.CorExpressOrderMapper;
import com.redescooter.ses.service.mobile.b.dm.base.CorExpressOrderExample;
import com.redescooter.ses.service.mobile.b.service.base.CorExpressOrderService;
@Service
public class CorExpressOrderServiceImpl extends ServiceImpl<CorExpressOrderMapper, CorExpressOrder> implements CorExpressOrderService{

    @Override
    public long countByExample(CorExpressOrderExample example) {
        return baseMapper.countByExample(example);
    }
    @Override
    public int deleteByExample(CorExpressOrderExample example) {
        return baseMapper.deleteByExample(example);
    }
    @Override
    public List<CorExpressOrder> selectByExample(CorExpressOrderExample example) {
        return baseMapper.selectByExample(example);
    }
    @Override
    public int updateByExampleSelective(CorExpressOrder record,CorExpressOrderExample example) {
        return baseMapper.updateByExampleSelective(record,example);
    }
    @Override
    public int updateByExample(CorExpressOrder record,CorExpressOrderExample example) {
        return baseMapper.updateByExample(record,example);
    }
}
