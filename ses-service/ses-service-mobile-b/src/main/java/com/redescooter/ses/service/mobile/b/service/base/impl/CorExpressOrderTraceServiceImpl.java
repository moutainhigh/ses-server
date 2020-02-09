package com.redescooter.ses.service.mobile.b.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.redescooter.ses.service.mobile.b.dm.base.CorExpressOrderTraceExample;
import com.redescooter.ses.service.mobile.b.dao.base.CorExpressOrderTraceMapper;
import com.redescooter.ses.service.mobile.b.dm.base.CorExpressOrderTrace;
import com.redescooter.ses.service.mobile.b.service.base.CorExpressOrderTraceService;
@Service
public class CorExpressOrderTraceServiceImpl extends ServiceImpl<CorExpressOrderTraceMapper, CorExpressOrderTrace> implements CorExpressOrderTraceService{

    @Override
    public long countByExample(CorExpressOrderTraceExample example) {
        return baseMapper.countByExample(example);
    }
    @Override
    public int deleteByExample(CorExpressOrderTraceExample example) {
        return baseMapper.deleteByExample(example);
    }
    @Override
    public List<CorExpressOrderTrace> selectByExample(CorExpressOrderTraceExample example) {
        return baseMapper.selectByExample(example);
    }
    @Override
    public int updateByExampleSelective(CorExpressOrderTrace record,CorExpressOrderTraceExample example) {
        return baseMapper.updateByExampleSelective(record,example);
    }
    @Override
    public int updateByExample(CorExpressOrderTrace record,CorExpressOrderTraceExample example) {
        return baseMapper.updateByExample(record,example);
    }
}
