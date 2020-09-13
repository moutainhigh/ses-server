package com.redescooter.ses.web.ros.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.redescooter.ses.web.ros.dao.base.SellsyCustomerMapper;
import com.redescooter.ses.web.ros.dm.SellsyCustomer;
import com.redescooter.ses.web.ros.service.base.SellsyCustomerService;
@Service
public class SellsyCustomerServiceImpl extends ServiceImpl<SellsyCustomerMapper, SellsyCustomer> implements SellsyCustomerService{

    @Override
    public int updateBatch(List<SellsyCustomer> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int batchInsert(List<SellsyCustomer> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(SellsyCustomer record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(SellsyCustomer record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
