package com.redescooter.ses.web.ros.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dao.base.OpeSaleScooterMapper;
import com.redescooter.ses.web.ros.dm.OpeSaleScooter;
import com.redescooter.ses.web.ros.service.base.OpeSaleScooterService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OpeSaleScooterServiceImpl extends ServiceImpl<OpeSaleScooterMapper, OpeSaleScooter> implements OpeSaleScooterService {

    @Override
    public int updateBatch(List<OpeSaleScooter> list) {
        return 0;
    }

    @Override
    public int batchInsert(List<OpeSaleScooter> list) {
        return 0;
    }

    @Override
    public int insertOrUpdate(OpeSaleScooter record) {
        return 0;
    }

    @Override
    public int insertOrUpdateSelective(OpeSaleScooter record) {
        return 0;
    }
}



