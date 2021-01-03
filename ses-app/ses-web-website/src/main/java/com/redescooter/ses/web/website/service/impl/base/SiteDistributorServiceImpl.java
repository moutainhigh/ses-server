package com.redescooter.ses.web.website.service.impl.base;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.redescooter.ses.web.website.dm.SiteDistributor;
import com.redescooter.ses.web.website.dao.base.SiteDistributorMapper;
import com.redescooter.ses.web.website.service.base.SiteDistributorService;
@Service
public class SiteDistributorServiceImpl extends ServiceImpl<SiteDistributorMapper, SiteDistributor> implements SiteDistributorService{

    @Override
    public int updateBatch(List<SiteDistributor> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int updateBatchSelective(List<SiteDistributor> list) {
        return baseMapper.updateBatchSelective(list);
    }
    @Override
    public int batchInsert(List<SiteDistributor> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(SiteDistributor record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(SiteDistributor record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
