package com.redescooter.ses.web.website.service.impl.base;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.redescooter.ses.web.website.dao.base.SiteProductMapper;
import com.redescooter.ses.web.website.dm.SiteProduct;
import com.redescooter.ses.web.website.service.base.SiteProductService;
@Service
public class SiteProductServiceImpl extends ServiceImpl<SiteProductMapper, SiteProduct> implements SiteProductService{

    @Override
    public int updateBatch(List<SiteProduct> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int updateBatchSelective(List<SiteProduct> list) {
        return baseMapper.updateBatchSelective(list);
    }
    @Override
    public int batchInsert(List<SiteProduct> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(SiteProduct record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(SiteProduct record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
