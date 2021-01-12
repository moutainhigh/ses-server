package com.redescooter.ses.web.ros.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dao.base.OpeInWhouseOrderMapper;
import com.redescooter.ses.web.ros.dm.OpeInWhouseOrder;
import com.redescooter.ses.web.ros.service.base.OpeInWhouseOrderService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OpeInWhouseOrderServiceImpl extends ServiceImpl<OpeInWhouseOrderMapper, OpeInWhouseOrder> implements OpeInWhouseOrderService {

    @Override
    public int updateBatch(List<OpeInWhouseOrder> list) {
        return 0;
    }

    @Override
    public int batchInsert(List<OpeInWhouseOrder> list) {
        return 0;
    }

    @Override
    public int insertOrUpdate(OpeInWhouseOrder record) {
        return 0;
    }

    @Override
    public int insertOrUpdateSelective(OpeInWhouseOrder record) {
        return 0;
    }
}







