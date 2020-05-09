package com.redescooter.ses.service.foundation.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.redescooter.ses.service.foundation.dao.base.PlaMailTemplateMapper;
import com.redescooter.ses.service.foundation.dm.base.PlaMailTemplate;
import com.redescooter.ses.service.foundation.service.base.PlaMailTemplateService;
@Service
public class PlaMailTemplateServiceImpl extends ServiceImpl<PlaMailTemplateMapper, PlaMailTemplate> implements PlaMailTemplateService{

    @Override
    public int batchInsert(List<PlaMailTemplate> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(PlaMailTemplate record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(PlaMailTemplate record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
    @Override
    public int insertOrUpdateWithBLOBs(PlaMailTemplate record) {
        return baseMapper.insertOrUpdateWithBLOBs(record);
    }
}
