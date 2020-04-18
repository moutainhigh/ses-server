package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.redescooter.ses.web.ros.dao.base.OpePurchasBMapper;
import com.redescooter.ses.web.ros.dm.OpePurchasB;
import com.redescooter.ses.web.ros.service.base.OpePurchasBService;

@Service
public class OpePurchasBServiceImpl extends ServiceImpl<OpePurchasBMapper, OpePurchasB> implements OpePurchasBService {

    @Override
    public int batchInsert(List<OpePurchasB> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpePurchasB record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpePurchasB record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}



















