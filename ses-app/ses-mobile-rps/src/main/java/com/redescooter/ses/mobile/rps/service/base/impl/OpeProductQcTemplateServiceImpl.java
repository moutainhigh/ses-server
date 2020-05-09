package com.redescooter.ses.mobile.rps.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redescooter.ses.mobile.rps.dao.base.OpeProductQcTemplateMapper;
import com.redescooter.ses.mobile.rps.dm.OpeProductQcTemplate;
import com.redescooter.ses.mobile.rps.service.base.OpeProductQcTemplateService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OpeProductQcTemplateServiceImpl extends ServiceImpl<OpeProductQcTemplateMapper, OpeProductQcTemplate> implements OpeProductQcTemplateService {

    @Override
    public int updateBatch(List<OpeProductQcTemplate> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<OpeProductQcTemplate> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(OpeProductQcTemplate record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OpeProductQcTemplate record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}


