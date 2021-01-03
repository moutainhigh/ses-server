package com.redescooter.ses.web.website.service.impl.base;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.redescooter.ses.web.website.dao.base.SiteProductColourMapper;
import com.redescooter.ses.web.website.dm.SiteProductColour;
import com.redescooter.ses.web.website.service.base.SiteProductColourService;
@Service
public class SiteProductColourServiceImpl extends ServiceImpl<SiteProductColourMapper, SiteProductColour> implements SiteProductColourService{

    @Override
    public int updateBatch(List<SiteProductColour> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int updateBatchSelective(List<SiteProductColour> list) {
        return baseMapper.updateBatchSelective(list);
    }
    @Override
    public int batchInsert(List<SiteProductColour> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(SiteProductColour record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(SiteProductColour record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
