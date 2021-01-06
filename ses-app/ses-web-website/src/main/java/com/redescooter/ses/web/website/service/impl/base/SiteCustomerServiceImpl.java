package com.redescooter.ses.web.website.service.impl.base;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.redescooter.ses.web.website.dm.SiteCustomer;
import com.redescooter.ses.web.website.dao.base.SiteCustomerMapper;
import com.redescooter.ses.web.website.service.base.SiteCustomerService;

@Service
public class SiteCustomerServiceImpl extends ServiceImpl<SiteCustomerMapper, SiteCustomer> implements SiteCustomerService {

    @Override
    public int updateBatch(List<SiteCustomer> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int updateBatchSelective(List<SiteCustomer> list) {
        return baseMapper.updateBatchSelective(list);
    }

    @Override
    public int batchInsert(List<SiteCustomer> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(SiteCustomer record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(SiteCustomer record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}


