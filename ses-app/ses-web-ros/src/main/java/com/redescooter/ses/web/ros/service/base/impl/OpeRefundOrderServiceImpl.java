package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.ros.dao.base.OpeRefundOrderMapper;
import java.util.List;
import com.redescooter.ses.web.ros.dm.OpeRefundOrder;
import com.redescooter.ses.web.ros.service.base.OpeRefundOrderService;

@Service
public class OpeRefundOrderServiceImpl extends ServiceImpl<OpeRefundOrderMapper, OpeRefundOrder>
    implements OpeRefundOrderService {

    @Override
    public int updateBatch(List<OpeRefundOrder> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeRefundOrder> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeRefundOrder record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeRefundOrder record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
