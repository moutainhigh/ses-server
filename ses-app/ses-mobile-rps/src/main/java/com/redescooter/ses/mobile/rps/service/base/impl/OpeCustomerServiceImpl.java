package com.redescooter.ses.mobile.rps.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.mobile.rps.dao.base.OpeCustomerMapper;
import java.util.List;
import com.redescooter.ses.mobile.rps.dm.OpeCustomer;
import com.redescooter.ses.mobile.rps.service.base.impl.OpeCustomerService;
@Service
public class OpeCustomerServiceImpl extends ServiceImpl<OpeCustomerMapper, OpeCustomer> implements OpeCustomerService{

    @Override
    public int updateBatch(List<OpeCustomer> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int batchInsert(List<OpeCustomer> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(OpeCustomer record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(OpeCustomer record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
