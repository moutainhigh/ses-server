package com.redescooter.ses.web.ros.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dao.base.OpeOutWhouseOrderMapper;
import com.redescooter.ses.web.ros.dm.OpeOutWhouseOrder;
import com.redescooter.ses.web.ros.service.base.OpeOutWhouseOrderService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OpeOutWhouseOrderServiceImpl extends ServiceImpl<OpeOutWhouseOrderMapper, OpeOutWhouseOrder> implements OpeOutWhouseOrderService {


    @Override
    public int updateBatch(List<OpeOutWhouseOrder> list) {
        return 0;
    }

    @Override
    public int batchInsert(List<OpeOutWhouseOrder> list) {
        return 0;
    }

    @Override
    public int insertOrUpdate(OpeOutWhouseOrder record) {
        return 0;
    }

    @Override
    public int insertOrUpdateSelective(OpeOutWhouseOrder record) {
        return 0;
    }
}




