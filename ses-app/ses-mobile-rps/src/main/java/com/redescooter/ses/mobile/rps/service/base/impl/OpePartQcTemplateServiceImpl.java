package com.redescooter.ses.mobile.rps.service.base.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.redescooter.ses.mobile.rps.dm.OpePartQcTemplate;
import com.redescooter.ses.mobile.rps.dao.base.OpePartQcTemplateMapper;
import com.redescooter.ses.mobile.rps.service.base.OpePartQcTemplateService;

@Service
public class OpePartQcTemplateServiceImpl extends ServiceImpl<OpePartQcTemplateMapper, OpePartQcTemplate> implements OpePartQcTemplateService {

    @Override
    public int updateBatch(List<OpePartQcTemplate> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpePartQcTemplate> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpePartQcTemplate record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpePartQcTemplate record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}


