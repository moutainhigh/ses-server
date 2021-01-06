package com.redescooter.ses.web.website.service.impl.base;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.website.dm.SiteProductParts;
import java.util.List;
import com.redescooter.ses.web.website.dao.base.SiteProductPartsMapper;
import com.redescooter.ses.web.website.service.base.SiteProductPartsService;

@Service
public class SiteProductPartsServiceImpl extends ServiceImpl<SiteProductPartsMapper, SiteProductParts> implements SiteProductPartsService {

    @Override
    public int updateBatch(List<SiteProductParts> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int updateBatchSelective(List<SiteProductParts> list) {
        return baseMapper.updateBatchSelective(list);
    }

    @Override
    public int batchInsert(List<SiteProductParts> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(SiteProductParts record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(SiteProductParts record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}



