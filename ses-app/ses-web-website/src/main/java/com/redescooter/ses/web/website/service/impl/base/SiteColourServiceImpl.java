package com.redescooter.ses.web.website.service.impl.base;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.web.website.dm.SiteColour;
import java.util.List;
import com.redescooter.ses.web.website.dao.base.SiteColourMapper;
import com.redescooter.ses.web.website.service.base.SiteColourService;
@Service
public class SiteColourServiceImpl extends ServiceImpl<SiteColourMapper, SiteColour> implements SiteColourService{

    @Override
    public int updateBatch(List<SiteColour> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int updateBatchSelective(List<SiteColour> list) {
        return baseMapper.updateBatchSelective(list);
    }
    @Override
    public int batchInsert(List<SiteColour> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(SiteColour record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(SiteColour record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
