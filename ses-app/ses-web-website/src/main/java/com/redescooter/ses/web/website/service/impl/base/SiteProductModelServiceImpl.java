package com.redescooter.ses.web.website.service.impl.base;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.redescooter.ses.web.website.dao.base.SiteProductModelMapper;
import com.redescooter.ses.web.website.dm.SiteProductModel;
import com.redescooter.ses.web.website.service.base.SiteProductModelService;

@Service
public class SiteProductModelServiceImpl extends ServiceImpl<SiteProductModelMapper, SiteProductModel> implements SiteProductModelService {

    @Override
    public int updateBatch(List<SiteProductModel> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int updateBatchSelective(List<SiteProductModel> list) {
        return baseMapper.updateBatchSelective(list);
    }

    @Override
    public int batchInsert(List<SiteProductModel> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(SiteProductModel record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(SiteProductModel record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}



