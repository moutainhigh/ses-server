package com.redescooter.ses.service.mobile.c.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.service.mobile.c.dm.base.ConUserScooter;
import java.util.List;
import com.redescooter.ses.service.mobile.c.dao.base.ConUserScooterMapper;
import com.redescooter.ses.service.mobile.c.service.base.ConUserScooterService;
@Service
public class ConUserScooterServiceImpl extends ServiceImpl<ConUserScooterMapper, ConUserScooter> implements ConUserScooterService{

    @Override
    public int updateBatch(List<ConUserScooter> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int batchInsert(List<ConUserScooter> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(ConUserScooter record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(ConUserScooter record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
